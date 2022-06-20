package com.ltd.data;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.Stack;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import com.ltd.configurations.PasswordConfig;
import com.ltd.logic.converters.JsonConverter;

@Entity
@Table(name = "Users")
public class UserEntity {
	@Id
	@Email
	private String email;
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;

	private String oldPasswords;

	private int loginTries = 0;
	private LocalTime forbidLoginTime;
	public static long minsBetweenLogins = 2;

	public UserEntity() {
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Lob
	public String getOldPasswords() {
		return oldPasswords;
	}

	public void setOldPasswords(String oldPasswords) {
		this.oldPasswords = oldPasswords;
	}

	public int getLoginTries() {
		return loginTries;
	}

	public void setLoginTries(int loginTries) {
		this.loginTries = loginTries;
	}

	public LocalTime getForbidLoginTime() {
		return forbidLoginTime;
	}

	public void setForbidLoginTime(LocalTime forbidLoginTime) {
		this.forbidLoginTime = forbidLoginTime;
	}

	public boolean tryLogin(UserEntity user) {

		if ((this.loginTries < PasswordConfig.MAX_TRIES_LOGIN)
				&& (forbidLoginTime == null || forbidLoginTime.isBefore(LocalTime.now()))) {
			loginTries++;
			return true;
		} else {
			forbidLoginTime = LocalTime.now().plusMinutes(minsBetweenLogins);
			loginTries = 0;
			return false;
		}
	}

	public void resetForbidLoginUntil() {
		this.forbidLoginTime = null;
	}

	public void updatePassword(String password, JsonConverter jsConverter) {
		Stack<String> allPasswords;
		allPasswords = jsConverter.JSONToStack(oldPasswords);
		if (allPasswords.size() >= PasswordConfig.HISTORY)
			allPasswords.remove(PasswordConfig.HISTORY - 1);
		allPasswords.push(this.password);
		this.oldPasswords = jsConverter.stackToJSON(allPasswords);
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationTime, email, oldPasswords, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		return Objects.equals(creationTime, other.creationTime) && Objects.equals(email, other.email)
				&& Objects.equals(oldPasswords, other.oldPasswords) && Objects.equals(password, other.password);
	}
}

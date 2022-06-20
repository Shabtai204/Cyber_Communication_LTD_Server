package com.ltd.boundaries;

import java.util.Date;
import java.util.Stack;

import javax.persistence.Lob;


public class UserBoundary {
	private String email;
	private String password;
	private String optionalPassword;
	private Date creationTime;
	
	private Stack<String> oldPasswords;
	
	public UserBoundary() {
		super();
		oldPasswords = new Stack<>();
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
	
	public String getOptionalPassword() {
		return optionalPassword;
	}

	public void setOptionalPassword(String optionalPassword) {
		this.optionalPassword = optionalPassword;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Lob
	public Stack<String> getOldPasswords() {
		return oldPasswords;
	}

	public void setOldPasswords(Stack<String> oldPasswords) {
		this.oldPasswords = oldPasswords;
	}
}

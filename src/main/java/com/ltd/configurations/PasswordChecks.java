package com.ltd.configurations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ltd.data.UserEntity;
import com.ltd.logic.converters.JsonConverter;

@Service
public class PasswordChecks {

	private JsonConverter jsConverter;
	private PasswordEncoder passEncoder;
	private Set<String> weakPasswords;

	@Autowired
	public void setJsConverter(JsonConverter jsConverter) {
		this.jsConverter = jsConverter;
	}
	
	@Autowired
	public void setPassEncoder(PasswordEncoder passEncoder) {
		this.passEncoder = passEncoder;
	}


	public PasswordChecks() {
		weakPasswords = new HashSet<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(PasswordConfig.DICTIONARY_FILE)))) {
			String line;
			while ((line = br.readLine()) != null) {
				weakPasswords.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkMail(String email) {
		boolean isValid = false;
		try {
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			isValid = true;
		} catch (AddressException e) {
			System.out.println("illegal Email: " + email);
		}
		return isValid;
	}

	public Boolean isPasswordInDictionary(String password) {
		if (!PasswordConfig.PREVENT_DICTIONARY)
			return false;

		if (weakPasswords.contains(password.toLowerCase())) {
			return true;
		}
		return false;
	}

	public boolean isPasswordInHistory(String newPassword, UserEntity ue) {
		Stack<String> userPasswords = jsConverter.JSONToStack(ue.getOldPasswords());
		for (int i = 0; i < userPasswords.size(); i++)
			if (passEncoder.matches(newPassword, userPasswords.elementAt(i)))
				return true;
		return false;
	}

	public boolean checkPassword(String password) {
		if (password.length() < PasswordConfig.PASS_MIN_LENGTH)
			return false;
		if (password.length() > PasswordConfig.PASS_MAX_LENGTH)
			return false;
		if (PasswordConfig.UPPERCASE && !isCharInRange(password, 'A', 'Z'))
			return false;
		if (PasswordConfig.LOWERCASE && !isCharInRange(password, 'a', 'z'))
			return false;
		if (PasswordConfig.DIGITS && !isCharInRange(password, '0', '9'))
			return false;
		if (PasswordConfig.SPECIAL && !isStringInRange(password, "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"))
			return false;
		return true;
	}
	
	public boolean isCharInRange(String str, char start, char end) {
		for (char i = start; i <= end; i++) {
			if (str.indexOf(i) != -1)
				return true;
		}
		return false;
	}

	public boolean isStringInRange(String str, CharSequence seq) {
		for (int i = 0; i < seq.length(); i++) {
			if (str.indexOf(seq.charAt(i)) != -1)
				return true;
		}
		return false;
	}

}

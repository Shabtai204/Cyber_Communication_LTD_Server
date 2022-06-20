package com.ltd.logic;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.ltd.configurations.PasswordConfig;

@Service
public class OtpService {
	private HashMap<String, String> tempOtp;

	public OtpService() {
		tempOtp = new HashMap<>();
	}
	
	public String generateRandomPassword() {
		final String chars = "0123456789" + "!@#$%^&*()[]<>|\\/|':,;?" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "abcdefghijklmnopqrstuvwxyz";

		SecureRandom random = new SecureRandom();
		StringBuilder genPassword = new StringBuilder();

		for (int i = 0; i < PasswordConfig.PASS_MAX_LENGTH; i++)
			genPassword.append(chars.charAt(random.nextInt(chars.length())));
		return genPassword.toString();
	}

	public String generateHash(String otk) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] byteOfTextToHash = otk.getBytes(StandardCharsets.UTF_8);
		byte[] hashedByetArray = digest.digest(byteOfTextToHash);
		String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
		return encoded;
	}

	public boolean newResetRequest(String email, String otk) {
		try {
			tempOtp.put(email, otk);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeResetRequest(String email) {
		try {
			tempOtp.remove(email);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean authenticate(String email, String otk) {
		if (tempOtp.isEmpty())
			return false;
		try {
			if (tempOtp.containsKey(email)) {
				String value = tempOtp.get(email);
				if (otk.equals(value)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public HashMap<String, String> getUserTempPass() {
		return tempOtp;
	}

	public void setUserTempPass(HashMap<String, String> tempOtp) {
		this.tempOtp = tempOtp;
	}
}

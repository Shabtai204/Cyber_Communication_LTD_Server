package com.ltd.utilities;

import org.springframework.stereotype.Service;

@Service
public class Validations {
	
	public void assertNull(Object obj) {
		if (obj == null)
			throw new BadRequestException("null object");
	}

	public void assertEmptyString(String str) {
		if (str.equals(""))
			throw new BadRequestException("Empty string");
	}

	public void assertValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!email.matches(regex))
			throw new BadRequestException("Invalid email");
	}	
}

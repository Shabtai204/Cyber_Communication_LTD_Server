package com.ltd.configurations;

public interface PasswordConfig {
	
	public final int
	PASS_MIN_LENGTH = 10,
	PASS_MAX_LENGTH = 18,
	HISTORY = 3,
	MAX_TRIES_LOGIN = 3;
	
	public final boolean
	UPPERCASE = true,
	LOWERCASE = true,
	DIGITS = true,
	SPECIAL = true;

	public final boolean 
	PREVENT_DICTIONARY = true;
	
	public final String 
	DICTIONARY_FILE = "darkwebdictionary.txt";

	public final String 
	SERVER_EMAIL = "commltd@outlook.com";


	
}

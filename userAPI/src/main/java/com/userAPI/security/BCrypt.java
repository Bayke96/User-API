package com.userAPI.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCrypt {

	public String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		String encryptedPass = encoder.encode(password);
		return encryptedPass;
	}
	
	public boolean verifyPassword(String passwordA, String passwordB) {
		boolean result = false;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		result = encoder.matches(passwordA, passwordB);
		return result;
	}
}

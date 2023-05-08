package com.example.SmartHouseAPI.service;

import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

	  private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxy";
	  private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	  private static final String NUMBER = "0123456789";
	  private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
	  
	  private static final String[] COMMON_PASSWORDS = {
			    "123456",
			    "password",
			    "123456789",
			    "12345",
			    "12345678",
			    "qwerty",
			    "1234567",
			    "111111",
			    "1234567890",
			    "123123",
			    "abc123",
			    "1234",
			    "password1",
			    "iloveyou",
			    "1q2w3e4r",
			    "000000",
			    "qwerty123",
			    "zaq12wsx",
			    "dragon",
			    "sunshine",
			    "princess",
			    "letmein",
			    "654321",
			    "monkey",
			    "27653",
			    "1qaz2wsx",
			    "123321",
			    "qwertyuiop",
			    "superman",
			    "asdfghjkl"
			};
	
	public boolean validatePassword(String password) {
		if (!includesAny(password, CHAR_LOWER)) return false;
		if (!includesAny(password, CHAR_UPPER)) return false;
		if (!includesAny(password, NUMBER)) return false;
		if (!includesAny(password, SPECIAL_CHARS)) return false;
		if (password.length() > 64 || password.length() < 12) return false;
		return true;
	}
	
	public boolean validatePin(String pin) {
		for (var ch: pin.toCharArray()) {
			if (!NUMBER.contains(Character.toString(ch))) return false;
			else continue;
		}
		return pin.length() == 4;
	}
	
	public boolean isCommonPassword(String password) {
		for(String common: COMMON_PASSWORDS) {
			if (common.equals(password)) return true;
			else continue;
		}
		return false;
	}
	
	private boolean includesAny(String target, String alphabet) {
		for(var ch: target.toCharArray()) {
			if (alphabet.contains(Character.toString(ch))) return true;
			else continue;
		}
		return false;
	}
}

package com.example.SmartHouseAPI.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class CredentialsGenerator {
	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;
    private static final String PASSWORD_ALLOW_UPPERCASE = CHAR_UPPER;
    private static final String PASSWORD_ALLOW_LOWERCASE = CHAR_LOWER;
    private static final String PASSWORD_ALLOW_NUMBER = NUMBER;
    private static final String PASSWORD_ALLOW_SPECIAL_CHARACTERS = SPECIAL_CHARS;

    private static SecureRandom random = new SecureRandom();

    public String generateRandomPassword() {
        StringBuilder sb = new StringBuilder(12);
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            char randomChar = PASSWORD_ALLOW_BASE.charAt(randomIndex);

            if (PASSWORD_ALLOW_UPPERCASE.contains(String.valueOf(randomChar))) {
                hasUpperCase = true;
            } else if (PASSWORD_ALLOW_LOWERCASE.contains(String.valueOf(randomChar))) {
                hasLowerCase = true;
            } else if (PASSWORD_ALLOW_NUMBER.contains(String.valueOf(randomChar))) {
                hasNumber = true;
            } else if (PASSWORD_ALLOW_SPECIAL_CHARACTERS.contains(String.valueOf(randomChar))) {
                hasSpecialChar = true;
            }

            sb.append(randomChar);
        }

        if (!hasUpperCase) {
            int randomIndex = random.nextInt(sb.length());
            char randomChar = PASSWORD_ALLOW_UPPERCASE.charAt(random.nextInt(PASSWORD_ALLOW_UPPERCASE.length()));
            sb.setCharAt(randomIndex, randomChar);
        }

        if (!hasLowerCase) {
            int randomIndex = random.nextInt(sb.length());
            char randomChar = PASSWORD_ALLOW_LOWERCASE.charAt(random.nextInt(PASSWORD_ALLOW_LOWERCASE.length()));
            sb.setCharAt(randomIndex, randomChar);
        }

        if (!hasNumber) {
            int randomIndex = random.nextInt(sb.length());
            char randomChar = PASSWORD_ALLOW_NUMBER.charAt(random.nextInt(PASSWORD_ALLOW_NUMBER.length()));
            sb.setCharAt(randomIndex, randomChar);
        }

        if (!hasSpecialChar) {
            int randomIndex = random.nextInt(sb.length());
            char randomChar = PASSWORD_ALLOW_SPECIAL_CHARACTERS.charAt(random.nextInt(PASSWORD_ALLOW_SPECIAL_CHARACTERS.length()));
            sb.setCharAt(randomIndex, randomChar);
        }

        return sb.toString();
    }
    
    public String generateRandomPin() {
        StringBuilder sb = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            int randomNum = random.nextInt(10);
            sb.append(randomNum);
        }

        return sb.toString();
    }
}

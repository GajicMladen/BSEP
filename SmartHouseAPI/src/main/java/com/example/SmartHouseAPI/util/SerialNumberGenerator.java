package com.example.SmartHouseAPI.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

public class SerialNumberGenerator {

   public static Long generateSerialNumber() throws NoSuchAlgorithmException {
	      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
	      byte[] randomBytes = new byte[8];
	      secureRandom.nextBytes(randomBytes);
	      
	      // Get current timestamp
	      Date now = new Date();
	      long time = now.getTime();
	      
	      // Combine timestamp and random number
	      byte[] timeBytes = BigInteger.valueOf(time).toByteArray();
	      byte[] serialBytes = new byte[timeBytes.length + randomBytes.length];
	      System.arraycopy(timeBytes, 0, serialBytes, 0, timeBytes.length);
	      System.arraycopy(randomBytes, 0, serialBytes, timeBytes.length, randomBytes.length);
	      
	      return Math.abs(new BigInteger(serialBytes).longValue());
	   }
}

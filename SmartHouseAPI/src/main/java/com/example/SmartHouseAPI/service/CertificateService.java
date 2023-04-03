package com.example.SmartHouseAPI.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class CertificateService {
	
	public KeyPair generateKeyPair(int keySize, String algorithm) {
		try {
			// SHA-1/RSA primer, treba samo RSA
			String algo = algorithm.split("/")[1];
			KeyPairGenerator kpGen = KeyPairGenerator.getInstance(algo);
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			kpGen.initialize(keySize, random);
			return kpGen.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

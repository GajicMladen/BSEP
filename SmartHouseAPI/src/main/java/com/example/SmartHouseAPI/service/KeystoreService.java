package com.example.SmartHouseAPI.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.springframework.stereotype.Service;

@Service
public class KeystoreService {

	private final String KEYSTORE_PATH = "./resources/keystore.jks";
	private KeyStore keyStore;
	
	public KeystoreService() {
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");
			loadKeyStore(KEYSTORE_PATH, "pass".toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public void loadKeyStore(String keyStoreFile, char[] password) throws CertificateException, IOException, NoSuchAlgorithmException {
        try {
            if (keyStoreFile != null) {
                keyStore.load(new FileInputStream(keyStoreFile), password);
            } else {
                keyStore.load(null, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.SmartHouseAPI.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;

@Service
public class KeystoreService {

	public final String KEYSTORE_PATH = "src/main/resources/keystore.jks";
	public final char[] PASSWORD = "pass".toCharArray();
	
	private KeyStore keyStore;
	
	public KeystoreService() {
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");
			loadKeyStore(KEYSTORE_PATH, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getCertificateAlias(X509Certificate cert) {
		try {
			return keyStore.getCertificateAlias(cert);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public Certificate getCertificateByAlias(String alias) {
        try {
            loadKeyStore(KEYSTORE_PATH, PASSWORD);
            if (keyStore.isKeyEntry(alias)) {
                Certificate cert = keyStore.getCertificate(alias);
                return cert;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    public List<Certificate> getAllCertificates() {
    	try {
    		List<Certificate> certificateList = new ArrayList<Certificate>();
    		Enumeration<String> aliases = keyStore.aliases();
    		while (aliases.hasMoreElements()) {
    			String alias = aliases.nextElement();
    			Certificate certificate = keyStore.getCertificate(alias);
    			if (certificate != null) {
    				certificateList.add(keyStore.getCertificate(alias));
    			}
    		}
    		return certificateList;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

        return new ArrayList<Certificate>();
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
    
    public PrivateKey getPrivateKey() throws Exception {
        loadKeyStore(KEYSTORE_PATH, PASSWORD);
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            Certificate cert = keyStore.getCertificate(alias);
            X509Certificate x509Cert = (X509Certificate) cert;
            X500Name x500name = new JcaX509CertificateHolder(x509Cert).getSubject();
            String cn = x500name.getRDNs(BCStyle.CN)[0].getFirst().getValue().toString();
             if (cn != null && cn.equals("Sertifikat za korisnike")) {
                Key key = keyStore.getKey(alias, PASSWORD);
                if (key instanceof PrivateKey) {
                    return (PrivateKey) key;
                }
            }
        }
        return null; // private key not found
    }
    
    public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persist(String alias, PrivateKey privateKey, Certificate certificate){
        write(alias, privateKey, PASSWORD, certificate);
        saveKeyStore();
    }
    
    public void saveKeyStore() {
        try {
            keyStore.store(new FileOutputStream(KEYSTORE_PATH), PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

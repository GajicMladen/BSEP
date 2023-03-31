package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.model.IssuerData;
import lombok.AllArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.springframework.util.ResourceUtils;

@Service
@AllArgsConstructor
public class KeystoreService {

    private final String keyStoreFile = "classpath:keystore.jks";
    private KeyStore keyStore;
    private final char[] PASS = "pass".toCharArray();

    public KeystoreService() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
            loadKeyStore(keyStoreFile);
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public IssuerData readIssuerFromStore(String alias, char[] password, char[] keyPass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);

            Certificate cert = keyStore.getCertificate(alias);
            PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getIssuer();
            return new IssuerData(issuerName, privKey);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException
                | UnrecoverableKeyException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Certificate readCertificateFromStore(String alias) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(ResourceUtils.getFile(keyStoreFile)));
            keyStore.load(in, PASS);

            if (keyStore.isKeyEntry(alias)) {
                Certificate cert = keyStore.getCertificate(alias);
                return cert;
            }
        } catch (KeyStoreException | NoSuchAlgorithmException
                | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrivateKey readPrivateKey(String alias, String pass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, PASS);

            if (keyStore.isKeyEntry(alias)) {
                PrivateKey pk = (PrivateKey) keyStore.getKey(alias, pass.toCharArray());
                return pk;
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException
                | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadKeyStore(String keyStoreFile) throws CertificateException, IOException, NoSuchAlgorithmException {
        try {
            if (keyStoreFile != null) {
                keyStore.load(new FileInputStream(keyStoreFile), PASS);
            } else {
                // Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
                keyStore.load(null, PASS);
            }
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            keyStore.load(null, PASS);
        }
    }

    public void saveKeyStore() {
        try {
            keyStore.store(new FileOutputStream(keyStoreFile), PASS);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public void persist(String alias, PrivateKey privateKey, char[] password, Certificate certificate){
        write(alias, privateKey, password, certificate);
        saveKeyStore();
    }
}

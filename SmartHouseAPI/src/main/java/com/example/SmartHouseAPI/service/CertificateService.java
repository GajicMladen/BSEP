package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.model.*;
import com.example.SmartHouseAPI.util.DateConverter;
import com.example.SmartHouseAPI.util.NameBuilderTransformer;
import lombok.AllArgsConstructor;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CertificateService {

    private final AdminService adminService;
    private final KeystoreService keystoreService;

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, Csr csrData) {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder(getContentSignerAlgorithm(csrData.getKeySize(), csrData.getAlgorithm()));
            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            addExtensions(certGen, csrData);

            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            return certConverter.getCertificate(certHolder);
        } catch (OperatorCreationException | CertificateException | CertIOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IssuerData buildIssuerData(String email, String algorithm, int keySize){
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        KeyPair keyPairSubject = generateKeyPair(algorithm, keySize);

        Long serialNumber = System.currentTimeMillis();

        Admin issuer = adminService.findByEmail(email);
        this.buildIssuerName(builder, issuer, serialNumber);

        return new IssuerData(builder.build(), keyPairSubject.getPrivate());
    }

    public SubjectData buildSubjectData(Csr csrData){
        try {
            KeyPair keyPairSubject = generateKeyPair(csrData.getAlgorithm(), csrData.getKeySize());
            Long serialNumber = System.currentTimeMillis();

            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            this.buildSubjectName(builder, csrData, serialNumber);

            return new SubjectData(keyPairSubject.getPublic(), builder.build(), serialNumber.toString(), DateConverter.localDateTimeToDate(csrData.getValidityStart()), DateConverter.localDateTimeToDate(csrData.getValidityEnd()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KeyPair generateKeyPair(String algorithm, int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyPairGenerator.initialize(keySize, random);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public X509Certificate saveCertificateToFile(final String alias) throws CertificateEncodingException, IOException, URISyntaxException {
        Certificate certificate = this.keystoreService.readCertificateFromStore(alias);
        saveCertificateToCrtFile(certificate);
        return (X509Certificate) certificate;
    }

    public static void saveCertificateToCrtFile(final Certificate certificate) throws IOException, CertificateEncodingException, URISyntaxException {
        X509Certificate x509Certificate = (X509Certificate) certificate;
        Path outputFile = Paths.get(String.format("src/main/resources/certificates/%s.crt",x509Certificate.getSerialNumber()));
        try (FileOutputStream fos = new FileOutputStream(outputFile.toFile())) {
            fos.write(certificate.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to save certificate to file", e);
        }
    }


    //region private methods
    private void buildIssuerName(X500NameBuilder builder, Admin issuer, Long serialNumber) {
        builder.addRDN(BCStyle.CN, issuer.getCommonName());
        builder.addRDN(BCStyle.SURNAME, issuer.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, issuer.getGivenName());
        builder.addRDN(BCStyle.O, issuer.getOrganization());
        builder.addRDN(BCStyle.OU, issuer.getOrganizationUnit());
        builder.addRDN(BCStyle.C, issuer.getCountry());
        builder.addRDN(BCStyle.E, issuer.getEmail());
        builder.addRDN(BCStyle.UID, issuer.getUid().toString());
        builder.addRDN(BCStyle.SERIALNUMBER, serialNumber.toString());
    }

    private void buildSubjectName(X500NameBuilder builder, Csr csrData, Long serialNumber) {
        for(Name name : csrData.getNames()){
            builder.addRDN(NameBuilderTransformer.transform(name.getAcronym()), name.getValue());
        }
        builder.addRDN(BCStyle.SERIALNUMBER, serialNumber.toString());
    }

    private String getContentSignerAlgorithm(int keySize, String algorithm){
        if(algorithm.equals("RSA")){
            if(keySize == 256) return "SHA256WithRSAEncryption";
            else return "SHA512WithRSAEncryption";
        }
        if(keySize == 256) return "SHA256withECDSA";
        else return "SHA384withECDSA";
    }

    private void addExtensions(X509v3CertificateBuilder certGen, Csr csrData) throws CertIOException {
        for(Extension e : csrData.getExtensions()){
            switch (e.getName()){
                case "Key Usage":
                    int usages = getKeyUsageFlags(e.getValue().stream().map(ExtensionPiece::getName).toList());
                    certGen.addExtension( org.bouncycastle.asn1.x509.Extension.keyUsage, true, new KeyUsage(usages));
                    break;
                case "Extended Key Usage":
                    ExtendedKeyUsage usage = getExtendedKeyUsage(e.getValue().stream().map(ExtensionPiece::getName).toList());
                    certGen.addExtension( org.bouncycastle.asn1.x509.Extension.extendedKeyUsage, e.isCritical(), usage);
                    break;
                case "Basic Constraints":
                    certGen.addExtension(org.bouncycastle.asn1.x509.Extension.basicConstraints,
                            e.isCritical(),
                            new BasicConstraints(e.getName().equals("isCA")) // true for CA, false for end-entity
                    );
                    break;
                default:
                    break;
            }
        }
    }

    private int getKeyUsageFlags(List<String> keyUsageList) {
        int keyUsageFlags = 0;
        for (String keyUsage : keyUsageList) {
            switch (keyUsage) {
                case "Digital Signature":
                    keyUsageFlags |= KeyUsage.digitalSignature;
                    break;
                case "Non Repudiation":
                    keyUsageFlags |= KeyUsage.nonRepudiation;
                    break;
                case "Key Encipherment":
                    keyUsageFlags |= KeyUsage.keyEncipherment;
                    break;
                case "Data Encipherment":
                    keyUsageFlags |= KeyUsage.dataEncipherment;
                    break;
                case "Key Agreement":
                    keyUsageFlags |= KeyUsage.keyAgreement;
                    break;
                case "Certificate Signing":
                    keyUsageFlags |= KeyUsage.keyCertSign;
                    break;
                case "CRL Sign":
                    keyUsageFlags |= KeyUsage.cRLSign;
                    break;
                case "Encipher Only":
                    keyUsageFlags |= KeyUsage.encipherOnly;
                    break;
                case "Decipher Only":
                    keyUsageFlags |= KeyUsage.decipherOnly;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid key usage flag: " + keyUsage);
            }
        }
        return keyUsageFlags;
    }

    private ExtendedKeyUsage getExtendedKeyUsage(List<String> extendedKeyUsageTypes) {
        Map<String, KeyPurposeId> keyPurposeIdMap = new HashMap<>();
        keyPurposeIdMap.put("TLS Web Server Authentication", KeyPurposeId.id_kp_serverAuth);
        keyPurposeIdMap.put("TLS Web Client Authentication", KeyPurposeId.id_kp_clientAuth);
        keyPurposeIdMap.put("Code Signing", KeyPurposeId.id_kp_codeSigning);
        keyPurposeIdMap.put("E-mail Protection", KeyPurposeId.id_kp_emailProtection);
        keyPurposeIdMap.put("Time Stamping", KeyPurposeId.id_kp_timeStamping);
        keyPurposeIdMap.put("OCSP Signing", KeyPurposeId.id_kp_OCSPSigning);

        List<KeyPurposeId> keyPurposeIds = new ArrayList<>();
        for (String extendedKeyUsageType : extendedKeyUsageTypes) {
            KeyPurposeId keyPurposeId = keyPurposeIdMap.get(extendedKeyUsageType);
            if (keyPurposeId == null) {
                throw new IllegalArgumentException("Invalid extended key usage type: " + extendedKeyUsageType);
            }
            keyPurposeIds.add(keyPurposeId);
        }

        KeyPurposeId[] keyPurposeIdArray = keyPurposeIds.toArray(new KeyPurposeId[0]);
        return new ExtendedKeyUsage(keyPurposeIdArray);
    }
    //endregion

}

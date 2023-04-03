package com.example.SmartHouseAPI.service;

import static com.example.SmartHouseAPI.util.SerialNumberGenerator.generateSerialNumber;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;
import com.example.SmartHouseAPI.enums.RequestStatus;
import com.example.SmartHouseAPI.repository.CsrRepository;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SmartHouseAPI.enums.RequestStatus;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.model.IssuerData;
import com.example.SmartHouseAPI.model.SubjectData;
import com.example.SmartHouseAPI.model.SubjectDataPK;
import com.example.SmartHouseAPI.repository.CsrRepository;

import lombok.AllArgsConstructor;;

@Service
@AllArgsConstructor
public class CertificateService {
	
	private final KeystoreService keyStoreService;
	private final CsrRepository csrRepository;
	private final EmailService emailService;
	
	private final String ROOT_ALIAS = "mladengajic007";


	
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, Csr csr,PrivateKey privateKey) {
		try {
			Security.addProvider(new BouncyCastleProvider());
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder(getAlgorithmString(csr.getAlgorithm()));
			builder = builder.setProvider("BC");
			ContentSigner cs = builder.build(issuerData.getPrivateKey());
			X509CertificateHolder certHolder = null;
			if (csr.getVersion() == 1) {
				X509v1CertificateBuilder certGen = new JcaX509v1CertificateBuilder(issuerData.getX500name(),
	                    new BigInteger(subjectData.getSerialNumber()),
	                    subjectData.getStartDate(),
	                    subjectData.getEndDate(),
	                    subjectData.getX500name(),
	                    subjectData.getPublicKey());
				certHolder = certGen.build(cs);
			} else {
				X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
						new BigInteger(subjectData.getSerialNumber()),
						subjectData.getStartDate(),
						subjectData.getEndDate(),
						subjectData.getX500name(),
						subjectData.getPublicKey());
				certHolder = certGen.build(cs);
			}
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");
            emailService.sendCreationEmail(csr.getEmail(),csr.getAlias(),privateKey.toString());
            return certConverter.getCertificate(certHolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public IssuerData buildIssuerData(String name) {
		try {
			X500Name issuerName = buildIssuerName(name);
			PrivateKey pk = keyStoreService.getPrivateKey();
			return new IssuerData(issuerName, pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SubjectDataPK buildSubjectData(Csr csr) {
        try {
            KeyPair kp = generateKeyPair(csr.getKeySize(), csr.getAlgorithm());
			Long serialNumber = generateSerialNumber();
            X500Name subjectName = buildSubjectName(csr, serialNumber);
            SubjectData subjectData = new SubjectData(kp.getPublic(), subjectName, serialNumber.toString(), csr.getStartDate(), csr.getEndDate());
            return new SubjectDataPK(subjectData, kp.getPrivate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
    public List<Csr> getAllCertificates() {
    	
        List<Certificate> certificateList = keyStoreService.getAllCertificates();
        List<Csr> csrs = new ArrayList<Csr>();
        for(Certificate c: certificateList) {
        	try {
        		csrs.add(convertFromCertificate((X509Certificate) c));
        	} catch (Exception e) {
        		e.printStackTrace();
        		continue;
        	}
        }

		try{
			csrs.removeIf(c -> !containsAlias(c.getAlias()));
			csrs.forEach( c -> c.setStatus( getByAlias(c.getAlias()).getStatus() ));
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return csrs;
    }
    
    public boolean validateCertificate(String alias) {
    	boolean isChainValid = validateChain(alias);
    	boolean areDatesValid = validateDates(alias);
    	boolean isRevoked = isCertificateRevoked(alias);
    	
    	return isChainValid && areDatesValid && !isRevoked;
    }
	
	public void cancelSertificate(String alias){

		Csr csr = getByAlias(alias);
		csr.setStatus(RequestStatus.CANCELED);
		csrRepository.save(csr);

	}

	private boolean containsAlias( final String alias){
		List<Csr> list = csrRepository.findAll();
		return list.stream().map( c ->  c.getAlias()).anyMatch(alias::equals);
	}
	private Csr getByAlias( String alias){
		return csrRepository.findByAlias(alias);
	}

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
	
	private boolean isCertificateRevoked(String alias) {
		Csr csr = csrRepository.findByAlias(alias);
		return csr.getStatus().equals(RequestStatus.CANCELED);
	}
	
	private boolean validateDates(String alias) {
		X509Certificate cert = (X509Certificate)keyStoreService.getCertificateByAlias(alias);
    	Date endDate = cert.getNotAfter();
    	Date startDate = cert.getNotBefore();
    	Date now = new Date();
    	return now.after(startDate) && now.before(endDate);
	}
	
	private boolean validateChain(String alias) {
		X509Certificate cert = (X509Certificate)keyStoreService.getCertificateByAlias(alias);
    	try {
    		X500Principal issuerDN = cert.getIssuerX500Principal();
    		String issuerName = issuerDN.getName();
    		String issuerCN = "";
    		String[] nameParts = issuerName.split(",");
    		for (String part : nameParts) {
    		    if (part.startsWith("CN=")) {
    		        issuerCN = part.substring(3);
    		        break;
    		    }
    		}
	    	Certificate intermediate = keyStoreService.getCertificateByAlias(issuerCN.toLowerCase() + " (mladen gajic)");
	    	Certificate root = keyStoreService.getCertificateByAlias(ROOT_ALIAS);
	    	cert.verify(intermediate.getPublicKey());
	    	intermediate.verify(root.getPublicKey());
	    	return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private X500Name buildSubjectName(Csr csr, Long serialNumber) {
		try {
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			
			builder.addRDN(BCStyle.CN, csr.getCommonName());
			builder.addRDN(BCStyle.O, csr.getOrganizationName());
			builder.addRDN(BCStyle.OU, csr.getOrganizationalUnit());
			builder.addRDN(BCStyle.L, csr.getLocality());
			builder.addRDN(BCStyle.ST, csr.getState());
			builder.addRDN(BCStyle.C, csr.getCountry());
			builder.addRDN(BCStyle.E, csr.getEmail());
	        builder.addRDN(BCStyle.SERIALNUMBER, serialNumber.toString());
	        return builder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private X500Name buildIssuerName(String name) {
		try {
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			
			builder.addRDN(BCStyle.C, "SR");
			builder.addRDN(BCStyle.L, "NS");
			builder.addRDN(BCStyle.O, "FTN");
			builder.addRDN(BCStyle.OU, "SIIT");
			builder.addRDN(BCStyle.CN, name);
	        return builder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getAlgorithmString(String algo) {
		String[] tokens = algo.split("/");
		String[] shaTokens = tokens[0].split("-");
		if (shaTokens[1].equals("1")) return "SHA1WithRSAEncryption";
		else if (shaTokens[1].equals("256")) return "SHA256WithRSAEncryption";
		else if (shaTokens[1].equals("512")) return "SHA512WithRSAEncryption";
		//Default
		return "SHA512WithRSAEncryption";
	}
	
	private Csr convertFromCertificate(X509Certificate certificate) throws CertificateEncodingException {
		Csr csr = new Csr();
		csr.setAlgorithm(getFormattedStringAlgorithm(certificate.getSigAlgName()));
		csr.setAlias(keyStoreService.getCertificateAlias(certificate));

		X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
		csr.setCommonName(getRDNString(x500name, BCStyle.CN));
		csr.setEmail(getRDNString(x500name, BCStyle.E));
		csr.setOrganizationName(getRDNString(x500name, BCStyle.O));
		csr.setOrganizationalUnit(getRDNString(x500name, BCStyle.OU));
		csr.setLocality(getRDNString(x500name, BCStyle.L));
		csr.setState(getRDNString(x500name, BCStyle.ST));
		csr.setCountry(getRDNString(x500name, BCStyle.C));
		csr.setKeySize(((RSAPublicKey)certificate.getPublicKey()).getModulus().bitLength());
		csr.setStartDate(certificate.getNotBefore());
		csr.setEndDate(certificate.getNotAfter());
		csr.setVersion(certificate.getVersion());

		return csr;
	}
	
	private String getFormattedStringAlgorithm(String algorithm) {
		String substring = algorithm.substring(3);
		String[] tokens = substring.split("with");
		return "SHA-" + tokens[0] + "/RSA";
	}
	
	private String getRDNString(X500Name name, ASN1ObjectIdentifier style) {
		RDN[] arr = name.getRDNs(style);
		if (arr.length > 0)
			return IETFUtils.valueToString(arr[0].getFirst().getValue());
		else 
			return "";
	}

	public X509Certificate saveCertificateToFile(final String alias) throws CertificateEncodingException, IOException {
		Certificate certificate = this.keyStoreService.getCertificateByAlias(alias);
		saveCertificateToCrtFile(certificate);
		return (X509Certificate) certificate;
	}

	public static void saveCertificateToCrtFile(final Certificate certificate) throws IOException, CertificateEncodingException {
		X509Certificate x509Certificate = (X509Certificate) certificate;
		Path outputFile = Paths.get(String.format("src/main/resources/certificates/%s.crt",x509Certificate.getSerialNumber()));
		try (FileOutputStream fos = new FileOutputStream(outputFile.toFile())) {
			fos.write(certificate.getEncoded());
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Failed to save certificate to file", e);
		}
	}
}

package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.service.CertificateService;
import com.example.SmartHouseAPI.service.KeystoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

@RestController
@RequestMapping(path = "/certificate")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateController {

    private final CertificateService certificateService;
    private final KeystoreService keystoreService;

    // For testing purposes
//    @GetMapping
//    public ResponseEntity<?> readCertificateFromKeyStore(@RequestParam String alias){
//        keystoreService.readCertificateFromStore("sifra123", alias);
//        return ResponseEntity.ok("Success");
//    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> downloadCertificate(@PathVariable final String alias) {
        try {
            X509Certificate certificate = this.certificateService.saveCertificateToFile(alias);
            Path certificatePath = Paths.get(String.format("src/main/resources/certificates/%s.crt",certificate.getSerialNumber()));
            byte[] certificateBytes = Files.readAllBytes(certificatePath);
            ByteArrayResource resource = new ByteArrayResource(certificateBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.crt");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/x-x509-ca-cert");

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CertificateEncodingException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cert/{alias}")
    public ResponseEntity<?> saveCertificateToFile(@PathVariable final String alias) throws CertificateEncodingException, IOException, URISyntaxException {

        this.certificateService.saveCertificateToFile(alias);
        return ResponseEntity.ok().body("");
    }
}

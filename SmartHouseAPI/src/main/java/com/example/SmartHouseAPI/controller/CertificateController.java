package com.example.SmartHouseAPI.controller;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartHouseAPI.dto.CsrDTO;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.service.CertificateService;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/certificate")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateController {
	
	private final CertificateService certificateService;

    @GetMapping
    @RequestMapping(path="/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CsrDTO> getAllCertificates(){
    	List<Csr> csrs = certificateService.getAllCertificates();
    	List<CsrDTO> dtos = new ArrayList<CsrDTO>();
    	csrs.forEach(x -> dtos.add(x.toDTO()));
    	return dtos;
    }
    
    @GetMapping
    @RequestMapping(path="/validate/{alias}")
    @ResponseStatus(HttpStatus.OK)
    public boolean validate(@PathVariable String alias) {
        return certificateService.validateCertificate(alias);
    }

    @GetMapping
    @RequestMapping(path="/cancel/{alias}")
    @ResponseStatus(HttpStatus.OK)
    public List<CsrDTO> cancelCertificates(@PathVariable String alias){
        certificateService.cancelSertificate(alias);
        List<Csr> csrs = certificateService.getAllCertificates();
        List<CsrDTO> dtos = new ArrayList<CsrDTO>();
        csrs.forEach(x -> dtos.add(x.toDTO()));
        return dtos;
    }


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
        }
    }
}

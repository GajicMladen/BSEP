package com.example.SmartHouseAPI.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartHouseAPI.dto.CsrDTO;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.service.CertificateService;

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
}

package com.example.SmartHouseAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartHouseAPI.dto.CsrDTO;
import com.example.SmartHouseAPI.enums.RequestStatus;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.service.CsrService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/csr")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CsrController {

    private final CsrService csrService;

    @PostMapping
    @RequestMapping(path="/sendCsr")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendCsr(@RequestBody @Valid final CsrDTO csr){
    	csrService.create(csr);
    }

    @RequestMapping(path="/all/{statusString}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(@PathVariable final String statusString){
    	try {
    		RequestStatus status = RequestStatus.valueOf(statusString.toString().toUpperCase());
    		return ResponseEntity.ok(this.csrService.getAll(status));
    	} catch(IllegalArgumentException e) {
    		return ResponseEntity
    				.status(HttpStatus.BAD_REQUEST)
    				.body("Invalid status value: " + statusString);
		}
    }
    
    @GetMapping
    @RequestMapping(path="/reject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void reject(@PathVariable final Long id){
    	Csr csr = csrService.findById(id);
    	csrService.reject(csr);
    }
}

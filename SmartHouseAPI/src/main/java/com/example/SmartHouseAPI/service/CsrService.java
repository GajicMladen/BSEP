package com.example.SmartHouseAPI.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SmartHouseAPI.dto.CsrDTO;
import com.example.SmartHouseAPI.enums.RequestStatus;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.repository.CsrRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CsrService {

    private final CsrRepository csrRepository;

    public Csr create(CsrDTO dto) {
        Csr csr = new Csr(dto);
        csrRepository.save(csr);
        return csr;
    }

    public List<CsrDTO> getAll() {
        List<Csr> csrs = csrRepository.findAll();
        return convertCsrsToDtos(csrs);
    }
    
    public List<CsrDTO> getAll(RequestStatus status) {
    	List<Csr> csrs = csrRepository.findByStatus(status);
    	return convertCsrsToDtos(csrs);
    }
    
    public void approve(Csr csr) {
    	
    }
    
    public void reject(Csr csr) {
    	csr.setStatus(RequestStatus.REJECTED);
    	csrRepository.save(csr);
    }

    public Csr findById(Long id) {
    	return csrRepository.findById(id).get();
    }
    
    private List<CsrDTO> convertCsrsToDtos(List<Csr> csrList) {
    	List<CsrDTO> dtos = new ArrayList<CsrDTO>();
        csrList.forEach(csr -> dtos.add(csr.toDTO()));
        return dtos;
    }
    
}

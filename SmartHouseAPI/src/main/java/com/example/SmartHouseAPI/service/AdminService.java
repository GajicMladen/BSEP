package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.model.Admin;
import com.example.SmartHouseAPI.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin findByEmail(String email){
        return adminRepository.findByEmail(email);
    }
}

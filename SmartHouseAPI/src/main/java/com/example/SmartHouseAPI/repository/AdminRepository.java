package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);
}

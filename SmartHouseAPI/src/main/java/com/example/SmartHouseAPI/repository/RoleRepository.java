package com.example.SmartHouseAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SmartHouseAPI.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}

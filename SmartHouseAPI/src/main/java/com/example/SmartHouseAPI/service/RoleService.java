package com.example.SmartHouseAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SmartHouseAPI.model.Role;
import com.example.SmartHouseAPI.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@SuppressWarnings("deprecation")
	public Role findById(Long id) {
		Role auth = this.roleRepository.getOne(id);
		return auth;
	}
	
	public Role findByName(String name) {
		Role roles = this.roleRepository.findByName(name);
		return roles;
	}
}

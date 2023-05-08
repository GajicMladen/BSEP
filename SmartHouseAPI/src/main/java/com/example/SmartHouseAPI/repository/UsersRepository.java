package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Long> {
	
	public User findByEmail(String email);
	
	public boolean existsByEmail(String email);
}

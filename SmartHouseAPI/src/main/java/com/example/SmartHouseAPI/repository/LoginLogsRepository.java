package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogsRepository extends JpaRepository<LoginLog, Long> {

}

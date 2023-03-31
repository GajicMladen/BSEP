package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.enums.RequestState;
import com.example.SmartHouseAPI.model.Csr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsrRepository extends JpaRepository<Csr, Long> {

    List<Csr> findByState(RequestState state);
}

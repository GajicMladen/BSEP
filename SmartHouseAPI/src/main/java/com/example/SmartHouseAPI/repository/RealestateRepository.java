package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.Realestate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RealestateRepository extends JpaRepository<Realestate,Long> {

    @Query("SELECT r FROM Realestate r INNER JOIN r.users u WHERE u.id = :id")
    List<Realestate> findByUserId(@Param("id") Long id);
}

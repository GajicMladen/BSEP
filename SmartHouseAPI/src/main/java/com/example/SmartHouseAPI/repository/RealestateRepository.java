package com.example.SmartHouseAPI.repository;

import com.example.SmartHouseAPI.model.Realestate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RealestateRepository extends JpaRepository<Realestate,Long> {

    @Query("select r from Realestate r inner join r.users users where users.id = ?1")
    List<Realestate> findByUserId(Long id);


}

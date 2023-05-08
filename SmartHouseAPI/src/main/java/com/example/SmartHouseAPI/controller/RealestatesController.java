package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.model.Realestate;
import com.example.SmartHouseAPI.service.RealestateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/realestates")
@CrossOrigin(origins = "http://localhost:4200")
public class RealestatesController {

    @Autowired
    private RealestateService realestateService;

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Realestate>> getAll(){
        List<Realestate> realestates = realestateService.getAll();
        return ResponseEntity.ok(realestates);
    }

    @GetMapping(path = "/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Realestate>> getForUser(@PathVariable Long id){
        List<Realestate> realestates = realestateService.getByUserId(id);
        return ResponseEntity.ok(realestates);
    }

    @GetMapping(path = "update/forUser/{userID}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> updateForUser(@PathVariable Long userID,@RequestParam List<Long> rsid){
        Boolean rez = realestateService.updateRealestatesForUser(userID,rsid);
        return ResponseEntity.ok(rez);
    }

}

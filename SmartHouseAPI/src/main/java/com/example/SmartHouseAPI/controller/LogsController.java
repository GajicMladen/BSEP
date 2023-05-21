package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.dto.LogDTO;
import com.example.SmartHouseAPI.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;


    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.logsService.getAll());
    }

    @PostMapping("/addNew")
    public ResponseEntity<Boolean> newLog(@RequestBody LogDTO logDTO){
        return ResponseEntity.ok(this.logsService.addNewLog(logDTO));
    }
}

package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.dto.LogDTO;
import com.example.SmartHouseAPI.dto.LogSendDTO;
import com.example.SmartHouseAPI.dto.LoginLogDTO;
import com.example.SmartHouseAPI.dto.LogsSerachDTO;
import com.example.SmartHouseAPI.model.Log;
import com.example.SmartHouseAPI.service.DroolsService;
import com.example.SmartHouseAPI.service.LogsService;
import com.example.SmartHouseAPI.service.SocketService;
import org.drools.core.util.Drools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;

    @Autowired
    private DroolsService droolsService;

    @Autowired
    private SocketService socketService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<LogSendDTO> logs = this.logsService.getAll();
        this.socketService.pushAlarm("ide gas");
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/addNew")
    public ResponseEntity<Log> newLog(@RequestBody LogDTO logDTO){
        Log newLog = this.logsService.addNewLog(logDTO);
        this.socketService.pushLog(newLog);
        if(newLog.isAlarm()){
            LogSendDTO alarmLog = this.logsService.checkAlarms(newLog);
            if(alarmLog != null)
                this.socketService.pushAlarm(newLog,alarmLog);
        }
        return ResponseEntity.ok(newLog);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<?> serachLogs(@RequestBody LogsSerachDTO logsSerachDTO){
        List<LogSendDTO> ret = this.logsService.searchLogs(logsSerachDTO);
        return ResponseEntity.ok(ret);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<?> getLoginLogs() {
        List<LoginLogDTO> logs = droolsService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(logs);
    }
}

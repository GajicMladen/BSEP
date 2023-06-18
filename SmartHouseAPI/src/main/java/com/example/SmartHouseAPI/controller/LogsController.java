package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.dto.LogDTO;
import com.example.SmartHouseAPI.dto.LogSendDTO;
import com.example.SmartHouseAPI.dto.LoginLogDTO;
import com.example.SmartHouseAPI.dto.LogsSerachDTO;
import com.example.SmartHouseAPI.model.Log;
import com.example.SmartHouseAPI.service.DeviceService;
import com.example.SmartHouseAPI.service.DroolsService;
import com.example.SmartHouseAPI.service.LogsService;
import com.example.SmartHouseAPI.service.SocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.macasaet.fernet.Key;
import com.macasaet.fernet.StringValidator;
import com.macasaet.fernet.Token;
import com.macasaet.fernet.Validator;
import org.drools.core.util.Drools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StreamUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<LogSendDTO> logs = this.logsService.getAll();
        this.socketService.pushAlarm("ide gas");
        return ResponseEntity.ok(logs);
    }
    @PostMapping("/addNew")
    public ResponseEntity<Log> newLog(@RequestBody String encryptedMessage) throws IOException {

        LogDTO logDTO = this.logsService.decryptLogDTO(encryptedMessage);

        Log newLog = this.logsService.addNewLog(logDTO);
        this.socketService.pushLog(newLog);
        if(newLog.isAlarm()){
            LogSendDTO alarmLog = this.logsService.checkAlarms(newLog);
            boolean isReadDataDevice = this.deviceService.isReadDataForDevice(newLog.getDeviceID());
            if(alarmLog != null && isReadDataDevice)
                this.socketService.pushAlarm(newLog,alarmLog);
        }
        return ResponseEntity.ok(newLog);
    }
    private static final String AES_ALGORITHM = "AES";

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

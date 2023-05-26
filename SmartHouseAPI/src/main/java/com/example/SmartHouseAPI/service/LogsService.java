package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.LogDTO;
import com.example.SmartHouseAPI.dto.LogSendDTO;
import com.example.SmartHouseAPI.dto.LogsSerachDTO;
import com.example.SmartHouseAPI.model.Device;
import com.example.SmartHouseAPI.model.Log;
import com.example.SmartHouseAPI.model.Realestate;
import com.example.SmartHouseAPI.repository.DeviceRepository;
import com.example.SmartHouseAPI.repository.LogsRepository;
import com.example.SmartHouseAPI.repository.RealestateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogsService {

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RealestateRepository realestateRepository;

    public List<LogSendDTO> getAll(){
        List<Log> logs = this.logsRepository.findAll();
        return makeDTOs(logs);
    }


    public Log addNewLog(LogDTO logDTO){
        try {
            Optional<Device> device = this.deviceRepository.findById(logDTO.getDeviceID());
            boolean isAlarm = false;
            if(device.isPresent()){
                if(device.get().getUp_limit() >= logDTO.getReceivedValue() || device.get().getDown_limit() < logDTO.getReceivedValue()) {
                    isAlarm = true;
                }
            }
            else{
                return null;
            }
            Log newLog = new Log(logDTO.getHouseID(), logDTO.getDeviceID(), logDTO.getExactTime(), logDTO.getReceivedValue(),isAlarm);
            return logsRepository.save(newLog);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }


    public List<LogSendDTO> searchLogs(LogsSerachDTO logsSerachDTO){
        List<Log> logs = new ArrayList<>();
        if( logsSerachDTO.getStartDate() == null && logsSerachDTO.getEndDate() == null) {
            logs = this.logsRepository.houseID(logsSerachDTO.getRealestateID());
        }
        else {
            LocalDateTime startDate = LocalDateTime.parse(logsSerachDTO.getStartDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endDate = LocalDateTime.parse(logsSerachDTO.getEndDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            logs = this.logsRepository.houseIDAndExactTimeGreaterThanEqualAndExactTimeLessThanEqual(logsSerachDTO.getRealestateID(),startDate,endDate);
        }
        if( logsSerachDTO.getDeviceID() != null){
            logs = logs.stream().filter( log ->  log.getDeviceID().toString().equals(logsSerachDTO.getDeviceID().toString()) ).collect(Collectors.toList());
        }
        return makeDTOs(logs);
    }

    public List<LogSendDTO> makeDTOs(List<Log> logs){
        List<LogSendDTO> ret = new ArrayList<>();
        for (Log log: logs ) {
            Realestate r = this.realestateRepository.getById(log.getHouseID());
            Device d = this.deviceRepository.getById(log.getDeviceID());
            ret.add( new LogSendDTO(r.getAddress(),d.getDescription(),
                    log.getExactTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    log.getReceivedValue(),log.isAlarm()));
        }
        return ret;
    }

}

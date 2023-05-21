package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.LogDTO;
import com.example.SmartHouseAPI.model.Log;
import com.example.SmartHouseAPI.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogsService {

    @Autowired
    private LogsRepository logsRepository;


    public List<Log> getAll(){
        return logsRepository.findAll();
    }

    public boolean addNewLog(LogDTO logDTO){
        try {
            Log newLog = new Log(logDTO.getHouseID(), logDTO.getDeviceID(), logDTO.getExactTime(), logDTO.getReceivedValue());
            logsRepository.save(newLog);
            return true;
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }

}

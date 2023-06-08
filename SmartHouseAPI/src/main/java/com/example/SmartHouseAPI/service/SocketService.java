package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.LogSendDTO;
import com.example.SmartHouseAPI.model.Log;
import com.example.SmartHouseAPI.model.Realestate;
import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.repository.RealestateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    @Autowired
    private RealestateRepository realestateRepository;

    @Autowired
    private SimpMessagingTemplate template;

    public void pushAlarm(String msg) {
        // pushing the data to websocket
        this.template.convertAndSend("/topic/greetings", msg);
    }

    public void pushLog(Log log){
        Realestate realestate = this.realestateRepository.getReferenceById(log.getHouseID().longValue());
        System.out.println("novi log: " + log.toString());
        for (User user:  realestate.getUsers() ) {
            System.out.println("saljem na 'topic/logs/"+user.getId().toString()+"' ");
            this.template.convertAndSend("/topic/logs/"+user.getId().toString(), log);
        }
    }
    public void pushAlarm(Log log,LogSendDTO logSend){
        Realestate realestate = this.realestateRepository.getReferenceById(log.getHouseID().longValue());
        System.out.println("novi alarm: " + log.toString());
        for (User user:  realestate.getUsers() ) {
            System.out.println("saljem na 'topic/alarms/"+user.getId().toString()+"' ");
            this.template.convertAndSend("/topic/alarms/"+user.getId().toString(), logSend);
        }
    }

}
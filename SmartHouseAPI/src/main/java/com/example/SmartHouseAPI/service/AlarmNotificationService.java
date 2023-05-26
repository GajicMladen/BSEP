package com.example.SmartHouseAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmNotificationService {

    @Autowired
    private SimpMessagingTemplate template;

    public void pushAlarm(String msg) {
        // pushing the data to websocket
        this.template.convertAndSend("/topic/greetings", msg);
    }

}
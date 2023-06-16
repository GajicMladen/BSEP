package com.example.SmartHouseAPI.service;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {
    public void rules() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("rules");

        kieSession.insert("Hello world!");

        kieSession.fireAllRules();
        kieSession.dispose();
    }
}

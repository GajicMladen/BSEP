package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.AuthenticationRequest;
import com.example.SmartHouseAPI.model.FailedLogin;
import com.example.SmartHouseAPI.model.LoginViolation;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DroolsService implements InitializingBean, DisposableBean {

    private KieSession kieSession;

    @Override
    public void afterPropertiesSet() throws Exception {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        kieSession = kc.newKieSession("rules");
    }

    public void insertAuthenticationRequest(AuthenticationRequest authenticationRequest) {
        kieSession.insert(new FailedLogin(authenticationRequest));
        kieSession.fireAllRules();
    }

    @Override
    public void destroy() throws Exception {
        if (kieSession != null) {
            kieSession.dispose();
        }
    }

    public boolean checkForLoginViolation(String email) {
        Collection<Object> allObjects = (Collection<Object>) kieSession.getObjects();
        for (Object object : allObjects) {
            if (object instanceof LoginViolation loginViolation) {
                if (loginViolation.getEmail().equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }
}

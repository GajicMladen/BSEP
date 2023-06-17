package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.AuthenticationRequest;
import com.example.SmartHouseAPI.dto.LoginLogDTO;
import com.example.SmartHouseAPI.enums.FailedLoginType;
import com.example.SmartHouseAPI.model.FailedLogin;
import com.example.SmartHouseAPI.model.LoginLog;
import com.example.SmartHouseAPI.model.LoginViolation;
import com.example.SmartHouseAPI.repository.LoginLogsRepository;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class DroolsService implements InitializingBean, DisposableBean {

    @Autowired
    private LoginLogsRepository loginLogsRepository;

    private KieSession kieSession;

    @Override
    public void afterPropertiesSet() throws Exception {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        kieSession = kc.newKieSession("rules");
        kieSession.setGlobal("droolsService", this);
    }

    public void insertFailedLogin(AuthenticationRequest authenticationRequest, FailedLoginType type) {
        FailedLogin failedLogin = new FailedLogin(authenticationRequest);
        failedLogin.setFailedLoginType(type);
        kieSession.insert(failedLogin);
        kieSession.fireAllRules();
    }

    public void saveLoginLog(LoginViolation loginViolation) {
        LoginLog loginLog = new LoginLog();
        loginLog.setEmail(loginViolation.getEmail());
        loginLog.setExecutionTime(loginViolation.getExecutionTime());
        loginLog.setMessage(getLoginLogMessage(loginViolation.getFailedLoginType()));
        loginLogsRepository.save(loginLog);
    }

    @Override
    public void destroy() throws Exception {
        if (kieSession != null) {
            kieSession.dispose();
        }
    }

    public List<LoginLogDTO> getAll() {
        List<LoginLog> logs = loginLogsRepository.findAll();
        return makeDTOs(logs);
    }

    public String getLoginLogMessage(FailedLoginType type) {
        return switch (type) {
            case EMAIL_PASSWORD -> "Unet je pogrešan email ili lozinka više od tri puta u roku od pola sata.";
            case PIN -> "Unet je pogrešan PIN više od tri puta u roku od pola sata.";
            case INACTIVE -> "Pokušano je prijavljivanje sa naloga koji nije aktiviran.";
        };
    }

    private List<LoginLogDTO> makeDTOs(List<LoginLog> logs) {
        List<LoginLogDTO> ret = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
        for (LoginLog ll : logs) {
            ret.add(new LoginLogDTO(
                    ll.getId(), formatter.format(ll.getExecutionTime()),
                    ll.getEmail(), ll.getMessage()
            ));
        }
        return ret;
    }
}

package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.model.Realestate;
import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.repository.RealestateRepository;
import com.example.SmartHouseAPI.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RealestateService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RealestateRepository realestateRepository;

    public List<Realestate> getAll(){
        return realestateRepository.findAll();
    }
    public List<Realestate> getByUserId(Long userId){
        return realestateRepository.findByUserId(userId);
    }

    public Boolean updateRealestatesForUser(Long userId, List<Long> rsids ){
        Optional<User> user = usersRepository.findById(userId);
        if(user.isPresent()) {
            List<Realestate> realestates = new ArrayList<>();
            for (Long rsid : rsids) {
                Optional<Realestate> rs =realestateRepository.findById(rsid);
                if(rs.isEmpty()) return false;
                realestates.add(rs.get());
            }
            user.get().setRealestates(realestates);
            usersRepository.save(user.get());
            for (Realestate rs : realestates) {
                realestateRepository.save(rs);
            }
            return true;
        }
        return false;
    }

    public Realestate getByID(Long realestateID){
        Optional<Realestate> realestate =this.realestateRepository.findById(realestateID);
        return realestate.orElse(null);
    }

}

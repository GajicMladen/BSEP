package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.enums.UserRole;
import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;


    public List<User> getAll(){
        return usersRepository.findAll();
    }

    public boolean deleteUser(Long userID){
        Optional<User> user = usersRepository.findById(userID);
        if(user.isPresent()){
            usersRepository.delete(user.get());
            return true;
        }
        return false;
    }

    public boolean changeUserRole(Long userID){
        Optional<User> user = usersRepository.findById(userID);
        if(user.isPresent()){
            if(user.get().getRole() == UserRole.OWNER)
                user.get().setRole(UserRole.USER);
            else if( user.get().getRole() == UserRole.USER)
                user.get().setRole(UserRole.OWNER);

            usersRepository.save(user.get());
            return true;
        }
        return false;
    }
}

package com.example.SmartHouseAPI.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartHouseAPI.dto.UserDTO;
import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.service.UsersService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "https://localhost:4200")
public class UsersController {

    @Autowired
    private UsersService usersService;


    @GetMapping(path = "/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> users = usersService.getAll();
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        for(User u: users)
        	dtos.add(new UserDTO(u));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(path = "/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        boolean res = usersService.deleteUser(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping(path = "/changeUserRole/{id}")
    public ResponseEntity<Boolean> changeRole(@PathVariable Long id){
        boolean res = usersService.changeUserRole(id);
        return ResponseEntity.ok(res);
    }

}

package com.example.SmartHouseAPI.controller;

import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    @Autowired
    private UsersService usersService;


    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = usersService.getAll();
        return ResponseEntity.ok(users);
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

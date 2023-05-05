package com.example.SmartHouseAPI.model;

import com.example.SmartHouseAPI.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "pin")
    private String pin;

    @Column(name = "role")
    private UserRole role;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_realestates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "realestate_id"))
    private List<Realestate> realestates;

    public User() {
    }

    public User(String name, String lastName, String email, String password, String pin,UserRole userRole) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pin = pin;
        this.role = userRole;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Realestate> getRealestates() {
        return realestates;
    }

    public void setRealestates(List<Realestate> realestates) {
        this.realestates = realestates;
    }

    public void addRealestate(Realestate realestate) {
        this.realestates.add(realestate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

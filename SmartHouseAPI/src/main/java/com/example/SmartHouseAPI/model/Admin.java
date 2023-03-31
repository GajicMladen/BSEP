package com.example.SmartHouseAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long uid;
    private String email;
    private String commonName;
    private String givenName;
    private String surname;
    private String organization;
    private String organizationUnit;
    private String country;

}

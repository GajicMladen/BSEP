package com.example.SmartHouseAPI.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String email;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    private String role;
    
    public boolean validate() {
    	return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") &&
    			(role.equals("ROLE_USER") || role.equals("ROLE_OWNER"));
    }
}

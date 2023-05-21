package com.example.SmartHouseAPI.dto;

import lombok.Data;

@Data
public class CredentialsDTO {

	String email;
	String oldPassword;
	String oldPin;
	String newPassword;
	String newPin;
	
}

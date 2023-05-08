package com.example.SmartHouseAPI.dto;

import lombok.Data;

@Data
public class CredentialsDTO {

	String oldPassword;
	String oldPin;
	String newPassword;
	String newPin;
	
}

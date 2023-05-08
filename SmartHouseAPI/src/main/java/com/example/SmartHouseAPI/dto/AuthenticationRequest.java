package com.example.SmartHouseAPI.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthenticationRequest {
	@NotBlank
	String email;
	@NotBlank
	String password;
	@NotBlank
	String pin;
}

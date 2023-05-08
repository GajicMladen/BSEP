package com.example.SmartHouseAPI.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.SmartHouseAPI.dto.CredentialsDTO;
import com.example.SmartHouseAPI.dto.RegistrationDTO;
import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.service.UsersService;
import com.example.SmartHouseAPI.service.ValidatorService;
import com.example.SmartHouseAPI.util.ErrMsg;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/reg", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

	@Autowired
	UsersService userService;
	
	@Autowired
	ValidatorService validatorService;
	
	@Autowired
	PasswordEncoder passEncoder;
	
	@PostMapping("/register")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> register (@RequestBody RegistrationDTO registrationDTO, UriComponentsBuilder ucBuilder) {
		Gson gson = new Gson();
		
		if(!registrationDTO.validate())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(gson.toJson(new ErrMsg("Uneti podaci su neispravni!")));
		
		
		User existingUser = userService.findByEmail(registrationDTO.getEmail());
		
		if (existingUser != null) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(gson.toJson(new ErrMsg("E-mail je već upotrebljen!")));
		}
		
		User newUser = userService.save(registrationDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
	
	@PostMapping("/changeCredentials")
	public ResponseEntity<?> changeCredentials(@RequestBody CredentialsDTO credentialsDTO) {
		Gson gson = new Gson();
		User u = userService.findByEmail(credentialsDTO.getEmail());
		
		if (!passEncoder.matches(credentialsDTO.getOldPassword(), u.getPassword()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(new ErrMsg("Neispravna lozinka!")));
		
		if (!u.getPin().equals(credentialsDTO.getOldPin()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(new ErrMsg("Neispravan PIN!")));
		
		String newPass = credentialsDTO.getNewPassword();
		String newPin = credentialsDTO.getNewPin();
		
		if (!newPass.equals("")) {
			if (!validatorService.validatePassword(newPass))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(new ErrMsg("Nova lozinka je neispravna!")));
			u.setPassword(passEncoder.encode(credentialsDTO.getNewPassword()));
		}
		
		if (!newPin.equals("")) {
			if (validatorService.isCommonPassword(credentialsDTO.getNewPassword()))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(new ErrMsg("Nova lozinka se nalazi u listi najčešće korišćenih!")));
			u.setPin(credentialsDTO.getNewPin());
		}
	
		userService.save(u);
		return ResponseEntity.ok(null);
	}
}

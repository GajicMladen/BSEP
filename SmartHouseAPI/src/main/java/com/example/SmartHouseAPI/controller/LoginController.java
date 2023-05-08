package com.example.SmartHouseAPI.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartHouseAPI.dto.AuthenticationRequest;
import com.example.SmartHouseAPI.dto.UserTokenState;
import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.service.UsersService;
import com.example.SmartHouseAPI.util.ErrMsg;
import com.example.SmartHouseAPI.util.TokenUtils;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsersService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response 
			){
		Gson gson = new Gson();
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch(AuthenticationException e) {
			if (userService.existsByEmail(authenticationRequest.getEmail()))
				userService.registerUnsuccessfulLogin(authenticationRequest.getEmail());
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(gson.toJson(new ErrMsg("Neispravan e-mail ili lozinka!")));
		}
		
        SecurityContextHolder.getContext().setAuthentication(authentication);
		
        User user = (User) authentication.getPrincipal();
        
        if (!authenticationRequest.getPin().equals(user.getPin())) {
        	userService.registerUnsuccessfulLogin(user.getEmail());
        	SecurityContextHolder.clearContext();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(gson.toJson(new ErrMsg("Neispravan PIN!")));
        }

        if(!user.getActive()) {
        	SecurityContextHolder.clearContext();
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        			.body(gson.toJson(new ErrMsg("Vaš nalog je neaktivan!")));
        }
        
        user.setUnsuccessfulLoginAttempts(0);
        userService.save(user);
        String fingerprint = tokenUtils.generateFingerprint();
        String jwt = tokenUtils.generateToken(user.getUsername(), fingerprint);
        int expiresIn = tokenUtils.getExpiredIn();

        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);
        
        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok().headers(headers).body(new UserTokenState(jwt, expiresIn, user));
	}
	
}

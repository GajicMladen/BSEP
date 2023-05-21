package com.example.SmartHouseAPI.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.SmartHouseAPI.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	Long id;
	String firstName;
	String lastName;
	String email;
	List<String> roles;
	
	public UserDTO(User u) {
		this.id = u.getId();
		this.firstName = u.getName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.roles = new ArrayList<String>();
		u.getRoles().forEach((role) -> this.roles.add(role.getName()));
	}
}

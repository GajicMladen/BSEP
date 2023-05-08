package com.example.SmartHouseAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SmartHouseAPI.dto.RegistrationDTO;
import com.example.SmartHouseAPI.model.Role;
import com.example.SmartHouseAPI.model.User;
import com.example.SmartHouseAPI.repository.UsersRepository;
import com.example.SmartHouseAPI.util.CredentialsGenerator;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CredentialsGenerator credentialsGenerator;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    public List<User> getAll(){
        return usersRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		User user = usersRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
		} else {
			return user;
		}
	}
    
    public User findByEmail(String email) {
    	return usersRepository.findByEmail(email);
    }
    
    public User save(RegistrationDTO registrationDTO) {
    	User user = new User();
    	
    	user.setEmail(registrationDTO.getEmail());
    	user.setName(registrationDTO.getName());
    	user.setLastName(registrationDTO.getLastName());
    	user.setActive(true);
    	
    	String password = credentialsGenerator.generateRandomPassword();
    	String pin = credentialsGenerator.generateRandomPin();
    	
    	user.setPassword(passwordEncoder.encode(password));
    	user.setPin(pin);
    	
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleService.findByName("ROLE_USER"));
		user.setRoles(roles);
		
		emailService.sendRegistrationEmail(user.getEmail(), password, pin);
		return usersRepository.save(user);
    }
    
    public User save(User user) {
    	return usersRepository.save(user);
    }
    
    public boolean existsByEmail(String email) {
    	return usersRepository.existsByEmail(email);
    }
    
    public void registerUnsuccessfulLogin(String email) {
    	User user = usersRepository.findByEmail(email);
    	user.setUnsuccessfulLoginAttempts(user.getUnsuccessfulLoginAttempts() + 1);
    	if (user.getUnsuccessfulLoginAttempts() >= 3) user.setActive(false);
    	usersRepository.save(user);
    }
    
    public boolean deleteUser(Long userID){
        Optional<User> user = usersRepository.findById(userID);
        if(user.isPresent()){
            usersRepository.delete(user.get());
            return true;
        }
        return false;
    }

    public boolean changeUserRole(Long userID){
//        Optional<User> user = usersRepository.findById(userID);
//        if(user.isPresent()){
//            if(user.get().getRole() == UserRole.OWNER)
//                user.get().setRole(UserRole.USER);
//            else if( user.get().getRole() == UserRole.USER)
//                user.get().setRole(UserRole.OWNER);
//
//            usersRepository.save(user.get());
//            return true;
//        }
//        return false;
    	return true;
    }
}

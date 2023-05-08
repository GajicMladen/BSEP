package com.example.SmartHouseAPI.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
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
    
    @Column(name = "active")
    private Boolean active;
    
    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;
    
    @Column(name = "unsuccessful_login_attempts")
    private Integer unsuccessfulLoginAttempts;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    protected List<Role> roles;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_realestates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "realestate_id"))
    private List<Realestate> realestates;

    public User() {
    	this.unsuccessfulLoginAttempts = 0;
    }

    public User(String name, String lastName, String email, String password, String pin, List<Role> userRoles, Boolean active) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pin = pin;
        this.roles = userRoles;
        this.active = active;
        this.unsuccessfulLoginAttempts = 0;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }
    
    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getUnsuccessfulLoginAttempts() {
		return unsuccessfulLoginAttempts;
	}

	public void setUnsuccessfulLoginAttempts(Integer unsuccessfulLoginAttempts) {
		this.unsuccessfulLoginAttempts = unsuccessfulLoginAttempts;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
    	return this.roles;
    }
    
    @Override
    public String getUsername() {
    	return this.email;
    }
    
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
    	return true;
    }
    
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
    
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
    	return true;
    }
    
    @JsonIgnore
    @Override
    public boolean isEnabled() {
    	return true;
    }
    
	public boolean hasRole(String roleName){
		for (Role role: roles) {
			if(role.getName().equals(roleName))
				return true;
		}
		return false;
	}
}

package com.example.SmartHouseAPI.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsrDTO {

	    @Id
	    @GeneratedValue(strategy= GenerationType.IDENTITY)
	    private Long id;
	    @NotBlank
	    private String commonName;
	    @NotBlank
	    private String organizationName;
	    @NotBlank
	    private String organizationalUnit;
	    @NotBlank
	    private String locality;
	    @NotBlank
	    private String state;
	    @Size(min = 2, max = 2)
	    @Pattern(regexp = "^[A-Z]+$")
	    private String country;
	    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
	    private String email;
	    @NotBlank
	    private String algorithm;
	    
	    private String status;
}

package com.example.SmartHouseAPI.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
	    
	    private Integer keySize;
	    private String alias;
	    private String startDate;
	    private String endDate;
	    private Integer version;
	    private String intermediateCertificate;
	    
}

package com.example.SmartHouseAPI.model;

import java.util.Date;

import com.example.SmartHouseAPI.dto.CsrDTO;
import com.example.SmartHouseAPI.enums.RequestStatus;

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

import static com.example.SmartHouseAPI.util.DateParser.parseDate;
import static com.example.SmartHouseAPI.util.DateParser.stringifyDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Csr {

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

    private Integer keySize;
    private Date startDate;
    private Date endDate;
    private String alias;
    private Integer version;
    
    private RequestStatus status;
    
    public Csr(CsrDTO dto) {
    	commonName = dto.getCommonName();
    	organizationName = dto.getOrganizationName();
    	organizationalUnit = dto.getOrganizationalUnit();
    	locality = dto.getLocality();
    	state = dto.getState();
    	country = dto.getCountry();
    	email = dto.getEmail();
    	algorithm = dto.getAlgorithm();
    	keySize = dto.getKeySize();
    	status = RequestStatus.PENDING;
    	
    	if (dto.getStartDate() != null)
    		startDate = parseDate(dto.getStartDate());
    	
    	if (dto.getEndDate() != null)
    		endDate = parseDate(dto.getEndDate());
    	
    	alias = dto.getAlias();
    	version = dto.getVersion();
    }
    
    public CsrDTO toDTO() {
    	CsrDTO dto = new CsrDTO();
    	dto.setId(id);
    	dto.setCommonName(commonName);
    	dto.setOrganizationName(organizationName);
    	dto.setOrganizationalUnit(organizationalUnit);
    	dto.setLocality(locality);
    	dto.setState(state);
    	dto.setCountry(country);
    	dto.setEmail(email);
    	dto.setAlgorithm(algorithm);
    	dto.setStatus(status.toString());
    	dto.setKeySize(keySize);
    	
    	if(startDate != null)
    		dto.setStartDate(stringifyDate(startDate));
    	
    	if(endDate != null)
    		dto.setEndDate(stringifyDate(endDate));
    	
    	dto.setAlias(alias);
    	dto.setVersion(version);
    	return dto;
    }
}

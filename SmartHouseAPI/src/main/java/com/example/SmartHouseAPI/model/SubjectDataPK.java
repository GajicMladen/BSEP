package com.example.SmartHouseAPI.model;

import java.security.PrivateKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDataPK {

	SubjectData subjectData;
	PrivateKey privateKey;
}

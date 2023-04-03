package com.example.SmartHouseAPI.model;

import java.security.PublicKey;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectData {
    private PublicKey publicKey;
    private X500Name x500name;
    private String serialNumber;
    private Date startDate;
    private Date endDate;
}

package com.example.SmartHouseAPI.model;

import java.security.PrivateKey;

import org.bouncycastle.asn1.x500.X500Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuerData {
    private X500Name x500name;
    private PrivateKey privateKey;
}

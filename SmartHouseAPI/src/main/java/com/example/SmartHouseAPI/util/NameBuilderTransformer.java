package com.example.SmartHouseAPI.util;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.style.BCStyle;

public class NameBuilderTransformer {

        public static ASN1ObjectIdentifier transform(String acronym){
            return switch (acronym) {
                case "CN" -> BCStyle.CN;
                case "OU" -> BCStyle.OU;
                case "O" -> BCStyle.O;
                case "L" -> BCStyle.L;
                case "C" -> BCStyle.C;
                case "CITY" -> BCStyle.POSTAL_CODE;
                case "STREET" -> BCStyle.STREET;
                case "SURNAME" -> BCStyle.SURNAME;
                default -> null;
            };
        }

}

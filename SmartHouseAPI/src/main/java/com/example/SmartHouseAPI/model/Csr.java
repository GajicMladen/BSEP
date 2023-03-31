package com.example.SmartHouseAPI.model;

import com.example.SmartHouseAPI.enums.RequestState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Csr {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String algorithm;

    private String alias;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Extension> extensions;

    private int keySize;

    private int version;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Name> names;

    private LocalDateTime validityStart;

    private int validityPeriod;

    private RequestState state;

    private String reasonForRejection;

    private LocalDateTime validityEnd;

    public LocalDateTime getValidityEnd(){
        return validityStart.plusYears(validityPeriod);
    }

    public int getKeySize(){
        return algorithm.equals("RSA") ? keySize * 8 : keySize;
    }
}

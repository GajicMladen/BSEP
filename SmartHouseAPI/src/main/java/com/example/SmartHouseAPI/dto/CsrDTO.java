package com.example.SmartHouseAPI.dto;

import com.example.SmartHouseAPI.enums.RequestState;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.model.Extension;
import com.example.SmartHouseAPI.model.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CsrDTO {

    private Long id;

    private String algorithm;

    private String alias;

    private String email;

    private List<Extension> extensions;

    private int keySize;

    private int version;

    private List<Name> names;

    private LocalDateTime validityStart;

    private int validityPeriod;

    private RequestState state;

    private LocalDateTime validityEnd;

    public CsrDTO(final Csr csr) {
        this.id = csr.getId();
        this.algorithm = csr.getAlgorithm();
        this.alias = csr.getAlias();
        this.email = csr.getEmail();
        this.extensions = csr.getExtensions();
        this.keySize = csr.getKeySize();
        this.version = csr.getVersion();
        this.names = csr.getNames();
        this.validityStart = csr.getValidityStart();
        this.validityPeriod = csr.getValidityPeriod();
        this.state = csr.getState();
        this.validityEnd = csr.getValidityEnd();
    }

    public static List<CsrDTO> fromCsrs(final List<Csr> csrs){
        List<CsrDTO> csrDTOs = new LinkedList<>();
        csrs.forEach(csr ->
            csrDTOs.add(new CsrDTO(csr))
        );

        return csrDTOs;
    }
}

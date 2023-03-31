package com.example.SmartHouseAPI.service;

import com.example.SmartHouseAPI.dto.CsrDTO;
import com.example.SmartHouseAPI.enums.RequestState;
import com.example.SmartHouseAPI.exception.EntityNotFoundException;
import com.example.SmartHouseAPI.exception.EntityType;
import com.example.SmartHouseAPI.model.Csr;
import com.example.SmartHouseAPI.model.IssuerData;
import com.example.SmartHouseAPI.model.SubjectData;
import com.example.SmartHouseAPI.repository.CsrRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.cert.X509Certificate;
import java.util.List;

import static com.example.SmartHouseAPI.dto.CsrDTO.fromCsrs;

@Service
@AllArgsConstructor
public class CsrService {

    private final CsrRepository csrRepository;
    private final CertificateService certificateService;
    private final KeystoreService keystoreService;

    public CsrDTO create(Csr dto) {
        dto.setState(RequestState.PENDING);

        return new CsrDTO(csrRepository.save(dto));
    }

    public List<CsrDTO> getAll() {
        return fromCsrs(csrRepository.findAll());
    }

    public CsrDTO getDTO(final Long id) throws EntityNotFoundException {

        return csrRepository.findById(id).map(CsrDTO::new).orElseThrow(() ->
            new EntityNotFoundException(EntityType.CSR));
    }

    public Csr get(final Long id) throws EntityNotFoundException {

        return csrRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(EntityType.CSR));
    }

    public CsrDTO approve(final Long csrId, final String adminEmail) throws EntityNotFoundException {
        Csr csr = get(csrId);

        csr.setState(RequestState.ACCEPTED);
        SubjectData subjectData = certificateService.buildSubjectData(csr);
        IssuerData issuerData = certificateService.buildIssuerData(adminEmail, csr.getAlgorithm(), csr.getKeySize());
        X509Certificate certificate = certificateService.generateCertificate(subjectData, issuerData, csr);
        keystoreService.persist(csr.getAlias(), issuerData.getPrivateKey(), "sifra_za_sertifikat".toCharArray(), certificate);
        csrRepository.save(csr);

        return new CsrDTO(csr);
    }

    public CsrDTO reject(final Long id, final String reasonForRejection) throws EntityNotFoundException {
        Csr csr = get(id);
        csr.setState(RequestState.REJECTED);
        csr.setReasonForRejection(reasonForRejection);
        csrRepository.save(csr);

        return new CsrDTO(csr);
    }

    public List<CsrDTO> getAllByStatus(final String status) {

        return fromCsrs(csrRepository.findByState(RequestState.valueOf(status)));
    }
}

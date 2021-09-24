package edu.strongsubgroup.agreement.service;

import edu.strongsubgroup.agreement.api.dto.AgreementDto;
import edu.strongsubgroup.agreement.api.dto.AgreementHistoryDto;
import edu.strongsubgroup.agreement.common.AgreementStatus;
import edu.strongsubgroup.agreement.model.Agreement;
import edu.strongsubgroup.agreement.repository.specification.AgreementSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgreementService {
    AgreementDto find(Long id);

    Page<AgreementDto> findAllBySpecification(AgreementSpecification specification, Pageable pageable);

    AgreementDto add(AgreementDto agreementDto);

    AgreementDto update(AgreementDto agreementDto, Long id);

    void delete(Long id);

    Agreement findById(Long id);

    void changeStatus(Agreement agreement, AgreementStatus status, String description);

    List<AgreementHistoryDto> findHistoryByAgreementId(Long id);
}

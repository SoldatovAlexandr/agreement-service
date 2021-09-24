package edu.strongsubgroup.agreement.api.mapper;

import edu.strongsubgroup.agreement.api.dto.AgreementDto;
import edu.strongsubgroup.agreement.api.dto.AgreementHistoryDto;
import edu.strongsubgroup.agreement.model.Agreement;
import edu.strongsubgroup.agreement.model.AgreementHistory;
import org.springframework.stereotype.Component;

@Component
public class AgreementMapper {
    public AgreementDto to(Agreement agreement) {
        return AgreementDto.builder()
                .id(agreement.getId())
                .status(agreement.getStatus())
                .createdAt(agreement.getCreatedAt())
                .description(agreement.getDescription())
                .merchantId(agreement.getMerchant().getId())
                .providerId(agreement.getProvider().getId())
                .build();
    }

    public AgreementHistoryDto to(AgreementHistory agreementHistory) {
        return AgreementHistoryDto.builder()
                .id(agreementHistory.getId())
                .agreementId(agreementHistory.getAgreement().getId())
                .description(agreementHistory.getDescription())
                .status(agreementHistory.getStatus())
                .createdAt(agreementHistory.getCreatedAt())
                .build();
    }
}

package edu.strongsubgroup.agreement.service.impl;

import edu.strongsubgroup.agreement.api.dto.AgreementDto;
import edu.strongsubgroup.agreement.api.dto.AgreementHistoryDto;
import edu.strongsubgroup.agreement.api.mapper.AgreementMapper;
import edu.strongsubgroup.agreement.common.AgreementStatus;
import edu.strongsubgroup.agreement.exception.NotFoundException;
import edu.strongsubgroup.agreement.model.Agreement;
import edu.strongsubgroup.agreement.model.AgreementHistory;
import edu.strongsubgroup.agreement.model.Merchant;
import edu.strongsubgroup.agreement.model.Provider;
import edu.strongsubgroup.agreement.repository.AgreementHistoryRepository;
import edu.strongsubgroup.agreement.repository.AgreementRepository;
import edu.strongsubgroup.agreement.repository.MerchantRepository;
import edu.strongsubgroup.agreement.repository.ProviderRepository;
import edu.strongsubgroup.agreement.repository.specification.AgreementSpecification;
import edu.strongsubgroup.agreement.service.AgreementService;
import edu.strongsubgroup.agreement.service.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;
    private final AgreementHistoryRepository agreementHistoryRepository;
    private final MerchantRepository merchantRepository;
    private final ProviderRepository providerRepository;
    private final AgreementMapper agreementMapper;
    private final DateTimeUtils dateTimeUtils;

    @Override
    public AgreementDto find(Long id) {
        return agreementMapper.to(findById(id));
    }

    @Override
    public Page<AgreementDto> findAllBySpecification(AgreementSpecification specification, Pageable pageable) {
        Page<Agreement> agreements = agreementRepository.findAll(specification, pageable);
        return agreements.map(agreementMapper::to);
    }

    @Transactional
    @Override
    public AgreementDto add(AgreementDto agreementDto) {
        Merchant merchant = merchantRepository.findById(agreementDto.getMerchantId())
                .orElseThrow(() -> new NotFoundException(String.format("Merchant with id [%s] not found", agreementDto.getMerchantId())));
        Provider provider = providerRepository.findById(agreementDto.getProviderId())
                .orElseThrow(() -> new NotFoundException(String.format("Provider with id [%s] not found", agreementDto.getProviderId())));

        Agreement agreement = Agreement.builder()
                .createdAt(dateTimeUtils.now())
                .description(agreementDto.getDescription())
                .merchant(merchant)
                .provider(provider)
                .build();

        changeStatus(agreement, AgreementStatus.DEFAULT, "A new agreement has been created.");
        return agreementMapper.to(agreement);
    }

    @Override
    public AgreementDto update(AgreementDto agreementDto, Long id) {
        Agreement agreement = findById(id);
        changeStatus(agreement, agreementDto.getStatus(), "Status changed by user.");
        return agreementMapper.to(agreement);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        changeStatus(findById(id), AgreementStatus.DELETED, "Agreement was removed manually.");
    }

    @Override
    public Agreement findById(Long id) {
        return agreementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Agreement with id [%s] not found", id)));
    }

    @Transactional
    @Override
    public void changeStatus(Agreement agreement, AgreementStatus status, String description) {
        agreement.setStatus(status);

        agreementRepository.save(agreement);

        agreementHistoryRepository.save(AgreementHistory.builder()
                .createdAt(dateTimeUtils.now())
                .status(status)
                .agreement(agreement)
                .description(description)
                .build()
        );
    }

    @Override
    public List<AgreementHistoryDto> findHistoryByAgreementId(Long id) {
        Agreement agreement = findById(id);
        return agreementHistoryRepository.findByAgreement(agreement)
                .stream()
                .map(agreementMapper::to)
                .collect(Collectors.toList());
    }
}

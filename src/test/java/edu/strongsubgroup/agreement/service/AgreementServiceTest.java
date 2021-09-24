package edu.strongsubgroup.agreement.service;

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
import edu.strongsubgroup.agreement.service.impl.AgreementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AgreementServiceTest {
    private final AgreementRepository agreementRepository = mock(AgreementRepository.class);
    private final AgreementHistoryRepository agreementHistoryRepository = mock(AgreementHistoryRepository.class);
    private final MerchantRepository merchantRepository = mock(MerchantRepository.class);
    private final ProviderRepository providerRepository = mock(ProviderRepository.class);
    private final DateTimeUtils dateTimeUtils = mock(DateTimeUtils.class);
    private final AgreementMapper agreementMapper = new AgreementMapper();

    private AgreementService agreementService;

    private AgreementDto agreementDto;
    private Agreement agreement;

    private static final LocalDateTime createdAt = LocalDateTime.parse(
            "2021-09-24 13:49",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    );

    @BeforeEach
    void setUp() {
        agreementService = new AgreementServiceImpl(
                agreementRepository,
                agreementHistoryRepository,
                merchantRepository,
                providerRepository,
                agreementMapper,
                dateTimeUtils);

        agreement = Agreement.builder()
                .description("Some description")
                .createdAt(createdAt)
                .status(AgreementStatus.DEFAULT)
                .merchant(Merchant.builder().id(2L).build())
                .provider(Provider.builder().id(3L).build())
                .build();

        agreement.setId(1L);

        agreementDto = AgreementDto.builder()
                .id(1L)
                .merchantId(2L)
                .providerId(3L)
                .description("Some description")
                .status(AgreementStatus.DEFAULT)
                .createdAt(createdAt)
                .build();
    }

    @Test
    void find_success() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));

        AgreementDto result = agreementService.find(1L);

        verify(agreementRepository, times(1)).findById(1L);
        assertEquals(agreementDto, result);
    }

    @Test
    void find_agreementNotFound() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> agreementService.find(1L));

        verify(agreementRepository, times(1)).findById(1L);
        assertEquals("Agreement with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void add_success() {
        ArgumentCaptor<AgreementHistory> historyArgumentCaptor = ArgumentCaptor.forClass(AgreementHistory.class);
        when(merchantRepository.findById(2L)).thenReturn(Optional.of(Merchant.builder().id(2L).build()));
        when(providerRepository.findById(3L)).thenReturn(Optional.of(Provider.builder().id(3L).build()));
        when(dateTimeUtils.now()).thenReturn(createdAt);

        AgreementDto result = agreementService.add(agreementDto);

        verify(merchantRepository, times(1)).findById(2L);
        verify(providerRepository, times(1)).findById(3L);
        verify(dateTimeUtils, times(2)).now();
        verify(agreementRepository, times(1)).save(any());
        verify(agreementHistoryRepository, times(1)).save(historyArgumentCaptor.capture());
        assertEquals(AgreementStatus.DEFAULT, result.getStatus());
        AgreementHistory history = historyArgumentCaptor.getValue();
        assertEquals("A new agreement has been created.", history.getDescription());
        assertEquals(AgreementStatus.DEFAULT, history.getStatus());
        assertEquals(createdAt, history.getCreatedAt());
    }

    @Test
    void add_merchantNotFound() {
        when(merchantRepository.findById(2L)).thenReturn(Optional.empty());
        when(providerRepository.findById(3L)).thenReturn(Optional.of(Provider.builder().id(3L).build()));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> agreementService.add(agreementDto));

        assertEquals("Merchant with id [2] not found", notFoundException.getMessage());
    }

    @Test
    void add_providerNotFound() {
        when(merchantRepository.findById(2L)).thenReturn(Optional.of(Merchant.builder().id(2L).build()));
        when(providerRepository.findById(3L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> agreementService.add(agreementDto));

        assertEquals("Provider with id [3] not found", notFoundException.getMessage());
    }

    @Test
    void update_success() {
        ArgumentCaptor<AgreementHistory> historyArgumentCaptor = ArgumentCaptor.forClass(AgreementHistory.class);
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));
        when(dateTimeUtils.now()).thenReturn(createdAt);

        agreementDto.setStatus(AgreementStatus.APPROVED);

        AgreementDto result = agreementService.update(agreementDto, 1L);

        verify(agreementRepository, times(1)).findById(1L);
        assertEquals(agreementDto, result);
        verify(agreementHistoryRepository, times(1)).save(historyArgumentCaptor.capture());
        AgreementHistory history = historyArgumentCaptor.getValue();
        assertEquals("Status changed by user.", history.getDescription());
        assertEquals(AgreementStatus.APPROVED, history.getStatus());
        assertEquals(createdAt, history.getCreatedAt());
    }

    @Test
    void update_agreementNotFound() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> agreementService.update(agreementDto, 1L));

        verify(agreementRepository, times(1)).findById(1L);
        assertEquals("Agreement with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void delete_success() {
        ArgumentCaptor<AgreementHistory> historyArgumentCaptor = ArgumentCaptor.forClass(AgreementHistory.class);
        ArgumentCaptor<Agreement> agreementArgumentCaptor = ArgumentCaptor.forClass(Agreement.class);
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));
        when(dateTimeUtils.now()).thenReturn(createdAt);

        agreementService.delete(1L);

        verify(agreementRepository, times(1)).findById(1L);
        verify(agreementRepository, times(1)).save(agreementArgumentCaptor.capture());
        verify(agreementHistoryRepository, times(1)).save(historyArgumentCaptor.capture());

        Agreement captureAgreement = agreementArgumentCaptor.getValue();
        AgreementHistory captureHistory = historyArgumentCaptor.getValue();

        assertEquals(AgreementStatus.DELETED, captureAgreement.getStatus());
        assertEquals(createdAt, captureHistory.getCreatedAt());
        assertEquals(AgreementStatus.DELETED, captureHistory.getStatus());
        assertEquals("Agreement was removed manually.", captureHistory.getDescription());
    }

    @Test
    void delete_agreementNotFound() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> agreementService.delete(1L));

        verify(agreementRepository, times(1)).findById(1L);
        assertEquals("Agreement with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void changeStatus_success() {
        ArgumentCaptor<AgreementHistory> historyArgumentCaptor = ArgumentCaptor.forClass(AgreementHistory.class);

        when(dateTimeUtils.now()).thenReturn(createdAt);

        agreementService.changeStatus(agreement, AgreementStatus.APPROVED, "change");

        verify(dateTimeUtils, times(1)).now();
        verify(agreementRepository, times(1)).save(any());
        verify(agreementHistoryRepository, times(1)).save(historyArgumentCaptor.capture());
        assertEquals(AgreementStatus.APPROVED, agreement.getStatus());
        AgreementHistory history = historyArgumentCaptor.getValue();
        assertEquals("change", history.getDescription());
        assertEquals(AgreementStatus.APPROVED, history.getStatus());
        assertEquals(createdAt, history.getCreatedAt());
    }

    @Test
    void findHistoryByAgreementId_success() {
        List<AgreementHistory> histories = List.of(AgreementHistory.builder()
                .description("some description")
                .agreement(agreement)
                .status(AgreementStatus.DEFAULT)
                .createdAt(createdAt)
                .build());

        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));
        when(agreementHistoryRepository.findByAgreement(agreement)).thenReturn(histories);

        List<AgreementHistoryDto> result = agreementService.findHistoryByAgreementId(1L);

        verify(agreementRepository, times(1)).findById(1L);
        verify(agreementHistoryRepository, times(1)).findByAgreement(agreement);
        assertEquals("some description", result.get(0).getDescription());
        assertEquals(1L, result.get(0).getAgreementId());
        assertEquals(createdAt, result.get(0).getCreatedAt());
        assertEquals(AgreementStatus.DEFAULT, result.get(0).getStatus());
    }

    @Test
    void findHistoryByAgreementId_agreementNotFound() {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> agreementService.findHistoryByAgreementId(1L));

        verify(agreementRepository, times(1)).findById(1L);
        assertEquals("Agreement with id [1] not found", notFoundException.getMessage());
    }
}

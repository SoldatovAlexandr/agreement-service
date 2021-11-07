package edu.strongsubgroup.agreement.service;

import edu.strongsubgroup.agreement.api.dto.MerchantDto;
import edu.strongsubgroup.agreement.api.mapper.MerchantMapper;
import edu.strongsubgroup.agreement.exception.NotFoundException;
import edu.strongsubgroup.agreement.model.Merchant;
import edu.strongsubgroup.agreement.repository.MerchantRepository;
import edu.strongsubgroup.agreement.service.impl.MerchantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MerchantServiceTest {

    private final MerchantRepository merchantRepository = Mockito.mock(MerchantRepository.class);
    private final DateTimeUtils dateTimeUtils = Mockito.mock(DateTimeUtils.class);
    private final MerchantMapper merchantMapper = new MerchantMapper();

    private Merchant merchant;

    private MerchantDto merchantDto;

    private MerchantService merchantService;

    @BeforeEach
    void setUp() {
        merchantService = new MerchantServiceImpl(merchantRepository, merchantMapper, dateTimeUtils);

        merchant = Merchant.builder()
                .id(1L)
                .name("Тестовый мерчант")
                .guid("test")
                .phoneNumber("9040001212")
                .isActive(true)
                .build();

        merchantDto = MerchantDto.builder()
                .id(1L)
                .name("Тестовый мерчант")
                .isActive(true)
                .guid("test")
                .phoneNumber("9040001212")
                .build();
    }

    @Test
    void find_success() {
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));

        MerchantDto resultDto = merchantService.find(1L);

        verify(merchantRepository).findById(1L);
        assertEquals(merchantDto, resultDto);
    }

    @Test
    void find_error() {
        when(merchantRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException =
                Assertions.assertThrows(NotFoundException.class, () -> merchantService.find(1L));
        verify(merchantRepository).findById(1L);
        assertEquals("Merchant with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void add_success() {
        when(merchantRepository.save(merchant)).thenReturn(merchant);

        MerchantDto resultDto = merchantService.add(merchantDto);

        verify(dateTimeUtils).now();
        verify(merchantRepository).save(any());
        assertEquals(merchantDto, resultDto);
    }

    @Test
    void update_success() {
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));
        when(merchantRepository.save(merchant)).thenReturn(merchant);

        MerchantDto resultDto = merchantService.update(merchantDto, 1L);

        verify(merchantRepository).findById(1L);
        verify(merchantRepository).save(merchant);
        assertEquals(merchantDto, resultDto);
    }

    @Test
    void update_error() {
        when(merchantRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException =
                Assertions.assertThrows(NotFoundException.class, () -> merchantService.update(merchantDto, 1L));

        verify(merchantRepository).findById(1L);
        assertEquals("Merchant with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void delete_success() {
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));

        merchantService.delete(1L);

        verify(merchantRepository).findById(1L);
        verify(merchantRepository).delete(merchant);
    }


    @Test
    void changeStatus() {
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));

        MerchantDto merchantDto = merchantService.changeStatus(1L);

        verify(merchantRepository).findById(1L);
        verify(merchantRepository).save(merchant);
        assertFalse(merchantDto.isActive());
    }
}

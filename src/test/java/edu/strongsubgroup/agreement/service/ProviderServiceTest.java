package edu.strongsubgroup.agreement.service;

import edu.strongsubgroup.agreement.api.dto.ProviderDto;
import edu.strongsubgroup.agreement.api.mapper.ProviderMapper;
import edu.strongsubgroup.agreement.exception.NotFoundException;
import edu.strongsubgroup.agreement.model.Provider;
import edu.strongsubgroup.agreement.repository.ProviderRepository;
import edu.strongsubgroup.agreement.service.impl.ProviderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProviderServiceTest {
    private final ProviderRepository providerRepository = Mockito.mock(ProviderRepository.class);
    private final DateTimeUtils dateTimeUtils = Mockito.mock(DateTimeUtils.class);
    private final ProviderMapper providerMapper = new ProviderMapper();

    private Provider provider;

    private ProviderDto providerDto;

    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        providerService = new ProviderServiceImpl(providerRepository, providerMapper, dateTimeUtils);

        provider = Provider.builder()
                .id(1L)
                .name("Тестовый провайдер")
                .guid("test")
                .phoneNumber("9040001212")
                .isActive(true)
                .build();

        providerDto = ProviderDto.builder()
                .id(1L)
                .name("Тестовый провайдер")
                .isActive(true)
                .guid("test")
                .phoneNumber("9040001212")
                .build();
    }

    @Test
    void find_success() {
        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        ProviderDto resultDto = providerService.find(1L);

        verify(providerRepository).findById(1L);
        assertEquals(providerDto, resultDto);
    }

    @Test
    void find_error() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException =
                Assertions.assertThrows(NotFoundException.class, () -> providerService.find(1L));
        verify(providerRepository).findById(1L);
        assertEquals("Provider with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void add_success() {
        when(providerRepository.save(provider)).thenReturn(provider);

        ProviderDto resultDto = providerService.add(providerDto);

        verify(dateTimeUtils).now();
        verify(providerRepository).save(any());
        assertEquals(providerDto, resultDto);
    }

    @Test
    void update_success() {
        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(providerRepository.save(provider)).thenReturn(provider);

        ProviderDto resultDto = providerService.update(providerDto, 1L);

        verify(providerRepository).findById(1L);
        verify(providerRepository).save(provider);
        assertEquals(providerDto, resultDto);
    }

    @Test
    void update_error() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException =
                Assertions.assertThrows(NotFoundException.class, () -> providerService.update(providerDto, 1L));

        verify(providerRepository).findById(1L);
        assertEquals("Provider with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void delete_success() {
        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));

        providerService.delete(1L);

        verify(providerRepository).findById(1L);
        verify(providerRepository).delete(provider);
    }
}

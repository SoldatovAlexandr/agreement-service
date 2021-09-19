package edu.strongsubgroup.agreement.service.impl;

import edu.strongsubgroup.agreement.api.dto.ProviderDto;
import edu.strongsubgroup.agreement.api.mapper.ProviderMapper;
import edu.strongsubgroup.agreement.exception.NotFoundException;
import edu.strongsubgroup.agreement.model.Provider;
import edu.strongsubgroup.agreement.repository.ProviderRepository;
import edu.strongsubgroup.agreement.repository.specification.ProviderSpecification;
import edu.strongsubgroup.agreement.service.DateTimeUtils;
import edu.strongsubgroup.agreement.service.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;
    private final DateTimeUtils dateTimeUtils;

    @Override
    public ProviderDto find(Long id) {
        Provider provider = findById(id);
        return providerMapper.to(provider);
    }

    @Override
    public Page<ProviderDto> findAllBySpecification(ProviderSpecification specification, Pageable pageable) {
        Page<Provider> providers = providerRepository.findAll(specification, pageable);
        return providers.map(providerMapper::to);
    }

    @Override
    public ProviderDto add(ProviderDto merchantDto) {
        Provider provider = providerMapper.to(merchantDto);
        provider.setCreatedAt(dateTimeUtils.now());
        providerRepository.save(provider);
        return providerMapper.to(provider);
    }

    @Override
    public ProviderDto update(ProviderDto merchantDto, Long id) {
        Provider provider = findById(id);
        provider.setGuid(merchantDto.getGuid());
        provider.setActive(merchantDto.isActive());
        provider.setName(merchantDto.getName());
        provider.setPhoneNumber(merchantDto.getPhoneNumber());
        providerRepository.save(provider);
        return providerMapper.to(provider);
    }

    @Override
    public void delete(Long id) {
        Provider provider = findById(id);
        providerRepository.delete(provider);
    }

    @Override
    public Provider findById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Provider with id [%s] not found", id)));
    }
}

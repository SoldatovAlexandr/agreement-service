package edu.strongsubgroup.agreement.service;

import edu.strongsubgroup.agreement.api.dto.ProviderDto;
import edu.strongsubgroup.agreement.model.Provider;
import edu.strongsubgroup.agreement.repository.specification.ProviderSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProviderService {
    ProviderDto find(Long id);

    Page<ProviderDto> findAllBySpecification(ProviderSpecification specification, Pageable pageable);

    ProviderDto add(ProviderDto merchantDto);

    ProviderDto update(ProviderDto merchantDto, Long id);

    void delete(Long id);

    Provider findById(Long id);
}

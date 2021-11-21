package edu.strongsubgroup.agreement.service;

import edu.strongsubgroup.agreement.api.dto.MerchantDto;
import edu.strongsubgroup.agreement.exception.DuplicateException;
import edu.strongsubgroup.agreement.model.Merchant;
import edu.strongsubgroup.agreement.repository.specification.MerchantSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MerchantService {
    MerchantDto find(Long id);

    Page<MerchantDto> findAllBySpecification(MerchantSpecification specification, Pageable pageable);

    MerchantDto add(MerchantDto merchantDto) throws DuplicateException;

    MerchantDto update(MerchantDto merchantDto, Long id);

    void delete(Long id);

    Merchant findById(Long id);

    MerchantDto changeStatus(Long id);
}

package edu.strongsubgroup.agreement.service.impl;

import edu.strongsubgroup.agreement.api.dto.MerchantDto;
import edu.strongsubgroup.agreement.api.mapper.MerchantMapper;
import edu.strongsubgroup.agreement.exception.DuplicateException;
import edu.strongsubgroup.agreement.exception.NotFoundException;
import edu.strongsubgroup.agreement.model.Merchant;
import edu.strongsubgroup.agreement.repository.MerchantRepository;
import edu.strongsubgroup.agreement.repository.specification.MerchantSpecification;
import edu.strongsubgroup.agreement.service.DateTimeUtils;
import edu.strongsubgroup.agreement.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    private final DateTimeUtils dateTimeUtils;

    @Override
    public MerchantDto find(Long id) {
        Merchant merchant = findById(id);
        return merchantMapper.to(merchant);
    }

    @Override
    public Page<MerchantDto> findAllBySpecification(MerchantSpecification specification, Pageable pageable) {
        Page<Merchant> merchants = merchantRepository.findAll(specification, pageable);
        return merchants.map(merchantMapper::to);
    }

    @Override
    public MerchantDto add(MerchantDto merchantDto) throws DuplicateException {
        Merchant merchant = merchantMapper.to(merchantDto);
        merchant.setCreatedAt(dateTimeUtils.now());
        if (merchantRepository.findByGuid(merchant.getGuid()).isPresent()) {
            throw new DuplicateException(String.format("Merchant with guid [%s] already exists.", merchant.getGuid()));
        }
        merchantRepository.save(merchant);
        return merchantMapper.to(merchant);
    }

    @Transactional
    @Override
    public MerchantDto update(MerchantDto merchantDto, Long id) {
        Merchant merchant = findById(id);
        merchant.setGuid(merchantDto.getGuid());
        merchant.setActive(merchantDto.isActive());
        merchant.setName(merchantDto.getName());
        merchant.setPhoneNumber(merchantDto.getPhoneNumber());
        merchantRepository.save(merchant);
        return merchantMapper.to(merchant);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Merchant merchant = findById(id);
        merchantRepository.delete(merchant);
    }

    @Override
    public Merchant findById(Long id) {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Merchant with id [%s] not found", id)));
    }

    @Transactional
    @Override
    public MerchantDto changeStatus(Long id) {
        Merchant merchant = findById(id);
        merchant.setActive(!merchant.isActive());
        merchantRepository.save(merchant);
        return merchantMapper.to(merchant);
    }
}

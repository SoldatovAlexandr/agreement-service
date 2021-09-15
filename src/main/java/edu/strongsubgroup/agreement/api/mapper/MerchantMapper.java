package edu.strongsubgroup.agreement.api.mapper;

import edu.strongsubgroup.agreement.api.dto.MerchantDto;
import edu.strongsubgroup.agreement.model.Merchant;
import org.springframework.stereotype.Component;

@Component
public class MerchantMapper {
    public MerchantDto to(Merchant merchant) {
        return MerchantDto.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .guid(merchant.getGuid())
                .phoneNumber(merchant.getPhoneNumber())
                .isActive(merchant.isActive())
                .createdAt(merchant.getCreatedAt())
                .build();
    }

    public Merchant to(MerchantDto merchantDto) {
        return Merchant.builder()
                .id(merchantDto.getId())
                .name(merchantDto.getName())
                .guid(merchantDto.getGuid())
                .phoneNumber(merchantDto.getPhoneNumber())
                .isActive(merchantDto.isActive())
                .build();
    }
}

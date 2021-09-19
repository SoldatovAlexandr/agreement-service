package edu.strongsubgroup.agreement.api.mapper;

import edu.strongsubgroup.agreement.api.dto.ProviderDto;
import edu.strongsubgroup.agreement.model.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {
    public ProviderDto to(Provider provider) {
        return ProviderDto.builder()
                .id(provider.getId())
                .name(provider.getName())
                .guid(provider.getGuid())
                .phoneNumber(provider.getPhoneNumber())
                .isActive(provider.isActive())
                .createdAt(provider.getCreatedAt())
                .build();
    }

    public Provider to(ProviderDto providerDto) {
        return Provider.builder()
                .id(providerDto.getId())
                .name(providerDto.getName())
                .guid(providerDto.getGuid())
                .phoneNumber(providerDto.getPhoneNumber())
                .isActive(providerDto.isActive())
                .build();
    }
}

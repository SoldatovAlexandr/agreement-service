package edu.strongsubgroup.agreement.api.mapper;

import edu.strongsubgroup.agreement.api.dto.RoleDto;
import edu.strongsubgroup.agreement.model.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    public RoleDto to(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName().name())
                .build();
    }

    public Set<RoleDto> fromSet(Set<Role> roles) {
        return roles.stream().map(this::to).collect(Collectors.toSet());
    }
}

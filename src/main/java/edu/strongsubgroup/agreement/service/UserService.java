package edu.strongsubgroup.agreement.service;

import edu.strongsubgroup.agreement.api.dto.UserDto;
import edu.strongsubgroup.agreement.model.User;
import edu.strongsubgroup.agreement.repository.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto find(Long id);

    Page<UserDto> findAllBySpecification(UserSpecification specification, Pageable pageable);

    UserDto add(UserDto merchantDto);

    UserDto update(UserDto merchantDto, Long id);

    void delete(Long id);

    User findById(Long id);
}

package edu.strongsubgroup.agreement.service.impl;

import edu.strongsubgroup.agreement.api.dto.UserDto;
import edu.strongsubgroup.agreement.api.mapper.UserMapper;
import edu.strongsubgroup.agreement.common.UserRoles;
import edu.strongsubgroup.agreement.exception.NotFoundException;
import edu.strongsubgroup.agreement.model.User;
import edu.strongsubgroup.agreement.repository.RoleRepository;
import edu.strongsubgroup.agreement.repository.UserRepository;
import edu.strongsubgroup.agreement.repository.specification.UserSpecification;
import edu.strongsubgroup.agreement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> {
                    log.error("User with login {} not found", login);
                    throw new UsernameNotFoundException("Пользователь не найден.");
                });
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().name())));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    @Override
    public UserDto find(Long id) {
        User user = findById(id);
        return userMapper.to(user);
    }

    @Override
    public Page<UserDto> findAllBySpecification(UserSpecification specification, Pageable pageable) {
        Page<User> users = userRepository.findAll(specification, pageable);
        return users.map(userMapper::to);
    }

    @Override
    public UserDto add(UserDto userDto) {
        User user = userMapper.to(userDto);
        user.setRoles(Set.of(roleRepository.findByName(UserRoles.ROLE_MANAGER)
                .orElseThrow(() -> new NotFoundException(String.format("Role with name [%s]", UserRoles.ROLE_MANAGER.name())))));
        userRepository.save(user);
        return userMapper.to(user);
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto, Long id) {
        User user = findById(id);
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return userMapper.to(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id [%s] not found", id)));
    }
}

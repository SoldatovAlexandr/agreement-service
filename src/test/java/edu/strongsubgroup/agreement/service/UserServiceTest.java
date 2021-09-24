package edu.strongsubgroup.agreement.service;

import edu.strongsubgroup.agreement.api.dto.RoleDto;
import edu.strongsubgroup.agreement.api.dto.UserDto;
import edu.strongsubgroup.agreement.api.mapper.UserMapper;
import edu.strongsubgroup.agreement.common.UserRoles;
import edu.strongsubgroup.agreement.exception.NotFoundException;
import edu.strongsubgroup.agreement.model.Role;
import edu.strongsubgroup.agreement.model.User;
import edu.strongsubgroup.agreement.repository.RoleRepository;
import edu.strongsubgroup.agreement.repository.UserRepository;
import edu.strongsubgroup.agreement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private final UserMapper userMapper = new UserMapper();

    private User user;

    private Role role;

    private UserDto userDto;

    private RoleDto roleDto;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, userMapper);

        role = Role.builder()
                .name(UserRoles.ROLE_MANAGER)
                .build();
        role.setId(1L);

        roleDto = RoleDto.builder()
                .id(1L)
                .name(UserRoles.ROLE_MANAGER.name())
                .build();

        user = User.builder()
                .login("test@agreement.ru")
                .password("testSecretPassword")
                .roles(Set.of(role))
                .build();
        user.setId(1L);

        userDto = UserDto.builder()
                .id(1L)
                .login("test@agreement.ru")
                .password("testSecretPassword")
                .roles(Set.of(roleDto))
                .build();
    }

    @Test
    void find_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto resultDto = userService.find(1L);

        verify(userRepository).findById(1L);
        assertEquals(userDto, resultDto);
    }

    @Test
    void find_error() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException =
                Assertions.assertThrows(NotFoundException.class, () -> userService.find(1L));
        verify(userRepository).findById(1L);
        assertEquals("User with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void add_success() {
        when(userRepository.save(user)).thenReturn(user);
        when(roleRepository.findByName(UserRoles.ROLE_MANAGER)).thenReturn(Optional.of(role));

        UserDto resultDto = userService.add(userDto);

        verify(userRepository).save(any());
        assertEquals(userDto, resultDto);
    }

    @Test
    void update_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserDto resultDto = userService.update(userDto, 1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        assertEquals(userDto, resultDto);
    }

    @Test
    void update_error() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException notFoundException =
                Assertions.assertThrows(NotFoundException.class, () -> userService.update(userDto, 1L));

        verify(userRepository).findById(1L);
        assertEquals("User with id [1] not found", notFoundException.getMessage());
    }

    @Test
    void delete_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
    }

}

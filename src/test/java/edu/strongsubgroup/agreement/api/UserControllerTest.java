package edu.strongsubgroup.agreement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.strongsubgroup.agreement.api.dto.RoleDto;
import edu.strongsubgroup.agreement.api.dto.UserDto;
import edu.strongsubgroup.agreement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserService userService = mock(UserService.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void get_user() throws Exception {
        UserDto dto = UserDto.builder()
                .id(1L)
                .login("login@gmail.com")
                .password("password")
                .roles(Set.of(RoleDto.builder().id(10L).name("USER_ROLE").build()))
                .build();

        when(userService.find(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("login", is("login@gmail.com")))
                .andExpect(jsonPath("password", is("password")))
                .andExpect(jsonPath("roles[0].id", is(10)))
                .andExpect(jsonPath("roles[0].name", is("USER_ROLE")));
    }

    @Test
    void add_user() throws Exception {
        UserDto requestDto = UserDto.builder()
                .login("login@gmail.com")
                .password("password")
                .build();

        UserDto responseDto = UserDto.builder()
                .id(1L)
                .login("login@gmail.com")
                .password("password")
                .roles(Set.of(RoleDto.builder().id(10L).name("USER_ROLE").build()))
                .build();

        when(userService.add(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/users/")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("login", is("login@gmail.com")))
                .andExpect(jsonPath("password", is("password")))
                .andExpect(jsonPath("roles[0].id", is(10)))
                .andExpect(jsonPath("roles[0].name", is("USER_ROLE")));
    }

    @Test
    void update_user() throws Exception {
        UserDto requestDto = UserDto.builder()
                .login("login@gmail.com")
                .password("password")
                .build();

        UserDto responseDto = UserDto.builder()
                .id(1L)
                .login("login@gmail.com")
                .password("password")
                .roles(Set.of(RoleDto.builder().id(10L).name("USER_ROLE").build()))
                .build();

        when(userService.update(any(), eq(1L))).thenReturn(responseDto);

        mockMvc.perform(put("/api/users/1")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("login", is("login@gmail.com")))
                .andExpect(jsonPath("password", is("password")))
                .andExpect(jsonPath("roles[0].id", is(10)))
                .andExpect(jsonPath("roles[0].name", is("USER_ROLE")));
    }

    @Test
    void delete_user() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}

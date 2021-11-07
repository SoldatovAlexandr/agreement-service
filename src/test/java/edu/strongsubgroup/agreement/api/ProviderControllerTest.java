package edu.strongsubgroup.agreement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.strongsubgroup.agreement.api.dto.ProviderDto;
import edu.strongsubgroup.agreement.service.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProviderControllerTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime createdAt = LocalDateTime.parse("2021-09-28 11:39", formatter);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ProviderService providerService = mock(ProviderService.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProviderController(providerService)).build();
    }

    @Test
    void get_provider() throws Exception {
        ProviderDto dto = ProviderDto.builder()
                .id(1L)
                .isActive(true)
                .guid("provider")
                .name("Первый провайдер")
                .createdAt(createdAt)
                .phoneNumber("+7(999) 999 9999")
                .build();

        when(providerService.find(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/providers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("active", is(true)))
                .andExpect(jsonPath("guid", is("provider")))
                .andExpect(jsonPath("name", is("Первый провайдер")))
                .andExpect(jsonPath("phoneNumber", is("+7(999) 999 9999")));
    }

    @Test
    void add_provider() throws Exception {
        ProviderDto requestDto = ProviderDto.builder()
                .isActive(true)
                .guid("provider")
                .name("Первый провайдер")
                .phoneNumber("+7(999) 999 9999")
                .build();

        ProviderDto responseDto = ProviderDto.builder()
                .id(1L)
                .isActive(true)
                .guid("provider")
                .name("Первый провайдер")
                .createdAt(createdAt)
                .phoneNumber("+7(999) 999 9999")
                .build();

        when(providerService.add(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/providers/")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("active", is(true)))
                .andExpect(jsonPath("guid", is("provider")))
                .andExpect(jsonPath("name", is("Первый провайдер")))
                .andExpect(jsonPath("phoneNumber", is("+7(999) 999 9999")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void update_provider() throws Exception {
        ProviderDto requestDto = ProviderDto.builder()
                .id(1L)
                .isActive(true)
                .guid("provider")
                .name("Первый провайдер")
                .phoneNumber("+7(999) 999 9999")
                .build();

        ProviderDto responseDto = ProviderDto.builder()
                .id(1L)
                .isActive(true)
                .guid("provider")
                .name("Первый провайдер")
                .createdAt(createdAt)
                .phoneNumber("+7(999) 999 9999")
                .build();

        when(providerService.update(any(), eq(1L))).thenReturn(responseDto);

        mockMvc.perform(put("/api/providers/1")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("active", is(true)))
                .andExpect(jsonPath("guid", is("provider")))
                .andExpect(jsonPath("name", is("Первый провайдер")))
                .andExpect(jsonPath("phoneNumber", is("+7(999) 999 9999")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void delete_provider() throws Exception {
        mockMvc.perform(delete("/api/providers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void activate_provider() throws Exception {
        ProviderDto requestDto = ProviderDto.builder()
                .id(1L)
                .isActive(true)
                .guid("provider")
                .name("Первый провайдер")
                .phoneNumber("9008888888")
                .build();

        ProviderDto responseDto = ProviderDto.builder()
                .id(1L)
                .isActive(false)
                .guid("provider")
                .name("Первый провайдер")
                .createdAt(createdAt)
                .phoneNumber("9008888888")
                .build();

        when(providerService.changeStatus(eq(1L))).thenReturn(responseDto);

        mockMvc.perform(put("/api/providers/activate/1")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("active", is(false)))
                .andExpect(jsonPath("guid", is("provider")))
                .andExpect(jsonPath("name", is("Первый провайдер")))
                .andExpect(jsonPath("phoneNumber", is("9008888888")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

}

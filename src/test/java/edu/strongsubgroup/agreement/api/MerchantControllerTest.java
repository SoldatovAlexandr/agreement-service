package edu.strongsubgroup.agreement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.strongsubgroup.agreement.api.dto.MerchantDto;
import edu.strongsubgroup.agreement.service.MerchantService;
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

public class MerchantControllerTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime createdAt = LocalDateTime.parse("2021-09-28 11:39", formatter);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MerchantService merchantService = mock(MerchantService.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MerchantController(merchantService)).build();
    }

    @Test
    void get_merchant() throws Exception {
        MerchantDto dto = MerchantDto.builder()
                .id(1L)
                .isActive(true)
                .guid("merchant")
                .name("Первый мерчант")
                .createdAt(createdAt)
                .phoneNumber("9008888888")
                .build();

        when(merchantService.find(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/merchants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("active", is(true)))
                .andExpect(jsonPath("guid", is("merchant")))
                .andExpect(jsonPath("name", is("Первый мерчант")))
                .andExpect(jsonPath("phoneNumber", is("9008888888")));
    }

    @Test
    void add_merchant() throws Exception {
        MerchantDto requestDto = MerchantDto.builder()
                .id(1L)
                .isActive(true)
                .guid("merchant")
                .name("Первый мерчант")
                .phoneNumber("9008888888")
                .build();

        MerchantDto responseDto = MerchantDto.builder()
                .id(1L)
                .isActive(true)
                .guid("merchant")
                .name("Первый мерчант")
                .createdAt(createdAt)
                .phoneNumber("9008888888")
                .build();

        when(merchantService.add(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/merchants/")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("active", is(true)))
                .andExpect(jsonPath("guid", is("merchant")))
                .andExpect(jsonPath("name", is("Первый мерчант")))
                .andExpect(jsonPath("phoneNumber", is("9008888888")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void update_merchant() throws Exception {
        MerchantDto requestDto = MerchantDto.builder()
                .id(1L)
                .isActive(true)
                .guid("merchant")
                .name("Первый мерчант")
                .phoneNumber("9008888888")
                .build();

        MerchantDto responseDto = MerchantDto.builder()
                .id(1L)
                .isActive(true)
                .guid("merchant")
                .name("Первый мерчант")
                .createdAt(createdAt)
                .phoneNumber("9008888888")
                .build();

        when(merchantService.update(any(), eq(1L))).thenReturn(responseDto);

        mockMvc.perform(put("/api/merchants/1")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("active", is(true)))
                .andExpect(jsonPath("guid", is("merchant")))
                .andExpect(jsonPath("name", is("Первый мерчант")))
                .andExpect(jsonPath("phoneNumber", is("9008888888")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void delete_merchant() throws Exception {
        mockMvc.perform(delete("/api/merchants/1"))
                .andExpect(status().isNoContent());
    }
}

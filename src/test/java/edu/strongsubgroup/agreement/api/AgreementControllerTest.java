package edu.strongsubgroup.agreement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.strongsubgroup.agreement.api.dto.AgreementDto;
import edu.strongsubgroup.agreement.api.dto.AgreementHistoryDto;
import edu.strongsubgroup.agreement.common.AgreementStatus;
import edu.strongsubgroup.agreement.service.AgreementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AgreementControllerTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime createdAt = LocalDateTime.parse("2021-09-28 11:39", formatter);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AgreementService agreementService = mock(AgreementService.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AgreementController(agreementService)).build();
    }

    @Test
    void get_agreement() throws Exception {
        AgreementDto dto = AgreementDto.builder()
                .id(1L)
                .merchantId(10L)
                .providerId(20L)
                .description("Описание")
                .status(AgreementStatus.APPROVED)
                .createdAt(createdAt)
                .build();

        when(agreementService.find(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/agreements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("merchantId", is(10)))
                .andExpect(jsonPath("providerId", is(20)))
                .andExpect(jsonPath("description", is("Описание")))
                .andExpect(jsonPath("status", is("APPROVED")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void get_historyAgreement() throws Exception {
        AgreementHistoryDto dto = AgreementHistoryDto.builder()
                .id(10L)
                .agreementId(1L)
                .description("Описание")
                .status(AgreementStatus.APPROVED)
                .createdAt(createdAt)
                .build();

        when(agreementService.findHistoryByAgreementId(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/agreements/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", is(10)))
                .andExpect(jsonPath("[0].agreementId", is(1)))
                .andExpect(jsonPath("[0].description", is("Описание")))
                .andExpect(jsonPath("[0].status", is("APPROVED")))
                .andExpect(jsonPath("[0].createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void add_agreement() throws Exception {
        AgreementDto requestDto = AgreementDto.builder()
                .merchantId(10L)
                .providerId(20L)
                .description("Описание")
                .status(AgreementStatus.APPROVED)
                .build();

        AgreementDto responseDto = AgreementDto.builder()
                .id(1L)
                .merchantId(10L)
                .providerId(20L)
                .description("Описание")
                .status(AgreementStatus.APPROVED)
                .createdAt(createdAt)
                .build();

        when(agreementService.add(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/agreements/")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("merchantId", is(10)))
                .andExpect(jsonPath("providerId", is(20)))
                .andExpect(jsonPath("description", is("Описание")))
                .andExpect(jsonPath("status", is("APPROVED")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void update_agreement() throws Exception {
        AgreementDto requestDto = AgreementDto.builder()
                .merchantId(10L)
                .providerId(20L)
                .description("Описание")
                .status(AgreementStatus.APPROVED)
                .build();

        AgreementDto responseDto = AgreementDto.builder()
                .id(1L)
                .merchantId(10L)
                .providerId(20L)
                .description("Описание")
                .status(AgreementStatus.APPROVED)
                .createdAt(createdAt)
                .build();

        when(agreementService.update(any(), eq(1L))).thenReturn(responseDto);

        mockMvc.perform(put("/api/agreements/1")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("merchantId", is(10)))
                .andExpect(jsonPath("providerId", is(20)))
                .andExpect(jsonPath("description", is("Описание")))
                .andExpect(jsonPath("status", is("APPROVED")))
                .andExpect(jsonPath("createdAt", is("28/09/2021 11:39:00")));
    }

    @Test
    void delete_agreement() throws Exception {
        mockMvc.perform(delete("/api/agreements/1"))
                .andExpect(status().isNoContent());
    }
}

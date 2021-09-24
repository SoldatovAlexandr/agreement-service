package edu.strongsubgroup.agreement.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.strongsubgroup.agreement.common.AgreementStatus;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AgreementDto {

    private Long id;

    private Long merchantId;

    private Long providerId;

    private String description;

    private AgreementStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}

package edu.strongsubgroup.agreement.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProviderDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "incorrect.guid.error")
    @NotBlank(message = "required.guid.error")
    @Size(max = 20, message = "long.guid.error")
    private String guid;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9 -]*$", message = "incorrect.name.error")
    @NotBlank(message = "required.name.error")
    @Size(max = 64, message = "long.name.error")
    private String name;

    @Pattern(regexp = "[+][1-9]([0-9]{0,2})[(][0-9]{3}[)] [0-9]{3} [0-9]{4}$", message = "incorrect.phone.number.error")
    @NotBlank(message = "required.phone.number.error")
    @Size(max = 18, message = "long.phone.number.error")
    private String phoneNumber;

    private boolean isActive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}

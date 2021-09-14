package edu.strongsubgroup.agreement.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MerchantDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "required.value.error")
    @NotBlank(message = "required.value.error")
    @Size(max = 20, message = "required.value.error")
    private String guid;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9 -]*$", message = "required.value.error")
    @NotBlank(message = "required.value.error")
    @Size(max = 64, message = "required.value.error")
    private String name;

    @Pattern(regexp = "^[0-9]{10}$", message = "required.value.error")
    @NotBlank(message = "required.value.error")
    @Size(max = 10, message = "required.value.error")
    private String phoneNumber;

    @NotBlank(message = "required.value.error")
    private boolean isActive;
}

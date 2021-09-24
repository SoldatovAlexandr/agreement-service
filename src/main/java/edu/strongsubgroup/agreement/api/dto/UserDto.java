package edu.strongsubgroup.agreement.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "required.value.error")
    @Size(max = 64, message = "required.value.error")
    private String password;

    @Email(message =  "required.value.error")
    @NotBlank(message = "required.value.error")
    @Size(max = 64, message = "required.value.error")
    private String login;

    private Set<RoleDto> roles;
}

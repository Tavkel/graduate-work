package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

/**
 * NewPassword
 */
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Validated
public class NewPassword {
    @Schema(description = "текущий пароль")
    @Size(min = 8, max = 16)
    @JsonProperty("currentPassword")
    private String currentPassword = null;

    @Schema(description = "новый пароль")
    @Size(min = 8, max = 16)
    @JsonProperty("newPassword")
    private String newPassword = null;
}

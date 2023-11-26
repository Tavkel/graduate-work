package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

/**
 * UpdateUser
 */
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Validated
public class UpdateUser {
    @Schema(description = "имя пользователя")
    @Size(min = 3, max = 10)
    @JsonProperty("firstName")
    private String firstName = null;

    @Schema(description = "фамилия пользователя")
    @Size(min = 3, max = 10)
    @JsonProperty("lastName")
    private String lastName = null;

    @Schema(description = "телефон пользователя")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @JsonProperty("phone")
    private String phone = null;
}

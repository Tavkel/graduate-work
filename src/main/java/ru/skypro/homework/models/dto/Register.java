package ru.skypro.homework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.models.enums.Roles;

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    @Schema(description = "логин")
    @Size(min = 4, max = 32)
    @JsonProperty("username")
    private String username;

    @Schema(description = "пароль")
    @Size(min = 8, max = 16)
    @JsonProperty("password")
    private String password;

    @Schema(description = "имя пользователя")
    @Size(min = 2, max = 16)
    @JsonProperty("firstName")
    private String firstName;

    @Schema(description = "фамилия пользователя")
    @Size(min = 2, max = 16)
    @JsonProperty("lastName")
    private String lastName;

    @Schema(description = "телефон пользователя")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "роль пользователя")
    private Roles role;
}

package ru.skypro.homework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Login {
    @Schema(description = "логин")
    @Size(min = 4, max = 32)
    @JsonProperty("username")
    private String username;

    @Schema(description = "пароль")
    @Size(min = 8, max = 16)
    @JsonProperty("password")
    private String password;
}

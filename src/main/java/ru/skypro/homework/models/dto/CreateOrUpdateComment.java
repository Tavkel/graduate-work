package ru.skypro.homework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateComment {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "текст комментария")
    @NotNull
    @Size(min = 8, max = 64)
    @JsonProperty("text")
    private String text = null;
}
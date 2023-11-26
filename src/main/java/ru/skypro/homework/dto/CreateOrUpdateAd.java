package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@EqualsAndHashCode
@Setter
@Getter
public class CreateOrUpdateAd {

    @Schema(description = "заголовок объявления")
    @Size(min = 4, max = 32)
    @JsonProperty("title")
    private String title = null;

    @Schema(description = "цена объявления")
    @Min(0)
    @Max(10000000)
    @JsonProperty("price")
    private Integer price = null;

    @Schema(description = "описание объявления")
    @Size(min = 8, max = 64)
    @JsonProperty("description")
    private String description = null;
}

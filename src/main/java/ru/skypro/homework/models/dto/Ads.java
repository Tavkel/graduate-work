package ru.skypro.homework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Ads
 */
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Ads {
    @Schema(description = "общее количество объявлений")
    @JsonProperty("count")
    private Integer count = null;

    @Schema(description = "")
    @JsonProperty("results")
    @Valid
    private List<Ad> results = null;
}

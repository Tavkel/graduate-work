package ru.skypro.homework.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

/**
 * ExtendedAd
 */
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Validated
public class ExtendedAd {
    @Schema(description = "id объявления")
    @JsonProperty("pk")
    private Integer pk = null;

    @Schema(description = "имя автора объявления")
    @JsonProperty("authorFirstName")
    private String authorFirstName = null;

    @Schema(description = "фамилия автора объявления")
    @JsonProperty("authorLastName")
    private String authorLastName = null;

    @Schema(description = "описание объявления")
    @JsonProperty("description")
    private String description = null;

    @Schema(description = "логин автора объявления")
    @JsonProperty("email")
    private String email = null;

    @Schema(description = "ссылка на картинку объявления")
    @JsonProperty("image")
    private String image = null;

    @Schema(description = "телефон автора объявления")
    @JsonProperty("phone")
    private String phone = null;

    @Schema(description = "цена объявления")
    @JsonProperty("price")
    private Integer price = null;

    @Schema(description = "заголовок объявления")
    @JsonProperty("title")
    private String title = null;
}

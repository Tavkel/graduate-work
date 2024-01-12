package ru.skypro.homework.models.dto;

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

    public ExtendedAd pk(Integer pk) {
        this.pk = pk;
        return this;
    }

    public ExtendedAd authorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
        return this;
    }

    public ExtendedAd authorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
        return this;
    }

    public ExtendedAd description(String description) {
        this.description = description;
        return this;
    }

    public ExtendedAd email(String email) {
        this.email = email;
        return this;
    }

    public ExtendedAd image(String image) {
        this.image = image;
        return this;
    }

    public ExtendedAd phone(String phone) {
        this.phone = phone;
        return this;
    }

    public ExtendedAd price(Integer price) {
        this.price = price;
        return this;
    }

    public ExtendedAd title(String title) {
        this.title = title;
        return this;
    }
}

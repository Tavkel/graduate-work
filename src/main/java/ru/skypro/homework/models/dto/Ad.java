package ru.skypro.homework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Ad {
    @Schema(description = "id автора объявления")
    @JsonProperty("author")
    private Integer author = null;

    @Schema(description = "ссылка на картинку объявления")
    @JsonProperty("image")
    private String image = null;

    @Schema(description = "id объявления")
    @JsonProperty("pk")
    private Integer pk = null;

    @Schema(description = "цена объявления")
    @JsonProperty("price")
    private Integer price = null;

    @Schema(description = "заголовок объявления")
    @JsonProperty("title")
    private String title = null;

    public Ad author(Integer author) {
        this.author = author;
        return this;
    }

    public Ad image(String image) {
        this.image = image;
        return this;
    }

    public Ad pk(Integer pk) {
        this.pk = pk;
        return this;
    }

    public Ad price(Integer price) {
        this.price = price;
        return this;
    }

    public Ad title(String title) {
        this.title = title;
        return this;
    }
}

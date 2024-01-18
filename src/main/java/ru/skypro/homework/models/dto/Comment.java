package ru.skypro.homework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Setter
@Getter
public class Comment {

    @Schema(description = "id автора комментария")
    @JsonProperty("author")
    private Integer author = null;

    @Schema(description = "ссылка на аватар автора комментария")
    @JsonProperty("authorImage")
    private String authorImage = null;

    @Schema(description = "имя создателя комментария")
    @JsonProperty("authorFirstName")
    private String authorFirstName = null;

    @Schema(description = "дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    @JsonProperty("createdAt")
    private Long createdAt = null;

    @Schema(description = "id комментария")
    @JsonProperty("pk")
    private Integer pk = null;

    @Schema(description = "текст комментария")
    @JsonProperty("text")
    private String text = null;

    public Comment author(Integer author) {
        this.author = author;
        return this;
    }

    public Comment authorImage(String authorImage) {
        this.authorImage = authorImage;
        return this;
    }

    public Comment authorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
        return this;
    }

    public Comment createdAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Comment pk(Integer pk) {
        this.pk = pk;
        return this;
    }

    public Comment text(String text) {
        this.text = text;
        return this;
    }
}

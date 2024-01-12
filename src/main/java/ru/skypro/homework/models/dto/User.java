package ru.skypro.homework.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import ru.skypro.homework.models.enums.Roles;

/**
 * User
 */
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Validated
public class User {
    @Schema(description = "id пользователя")
    @JsonProperty("id")
    private Integer id = null;

    @Schema(description = "логин пользователя")
    @JsonProperty("email")
    private String email = null;

    @Schema(description = "имя пользователя")
    @JsonProperty("firstName")
    private String firstName = null;

    @Schema(description = "фамилия пользователя")
    @JsonProperty("lastName")
    private String lastName = null;

    @Schema(description = "телефон пользователя")
    @JsonProperty("phone")
    private String phone = null;

    @Schema(description = "роль пользователя")
    @JsonProperty("role")
    private Roles role = null;

    @Schema(description = "ссылка на аватар пользователя")
    @JsonProperty("image")
    private String image = null;

    public User id(Integer id) {
        this.id = id;
        return this;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }

    public User role(Roles role) {
        this.role = role;
        return this;
    }

    public User image(String image) {
        this.image = image;
        return this;
    }
}
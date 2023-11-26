package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

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

    /**
     * роль пользователя
     */
    public enum RoleEnum {
        USER("USER"),

        ADMIN("ADMIN");

        private String value;

        RoleEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static RoleEnum fromValue(String text) {
            for (RoleEnum b : RoleEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @Schema(description = "роль пользователя")
    @JsonProperty("role")
    private RoleEnum role = null;

    @Schema(description = "ссылка на аватар пользователя")
    @JsonProperty("image")
    private String image = null;
}

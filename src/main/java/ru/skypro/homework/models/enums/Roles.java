package ru.skypro.homework.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * роль пользователя
 */
public enum Roles {

    USER("USER"),

    ADMIN("ADMIN");

    private String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Roles fromValue(String text) {
        for (Roles b : Roles.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

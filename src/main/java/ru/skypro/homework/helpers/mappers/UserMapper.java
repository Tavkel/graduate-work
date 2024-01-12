package ru.skypro.homework.helpers.mappers;

import ru.skypro.homework.models.domain.UserDomain;
import ru.skypro.homework.models.dto.Register;
import ru.skypro.homework.models.dto.UpdateUser;
import ru.skypro.homework.models.dto.User;

public class UserMapper {
    public static UserDomain registerToUserDomain(Register dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Tried to map null to UserDomain");
        }
        return new UserDomain()
                .email(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .userRole(dto.getRole());
    }

    public static User userDomainToUser(UserDomain model) {
        if (model == null) {
            throw new IllegalArgumentException("Tried to map null to User");
        }
        return new User()
                .id(model.getId())
                .email(model.getEmail())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .phone(model.getPhone())
                .role(model.getUserRole())
                .image(model.getImageUrl());
    }

    public static UpdateUser userDomainToUpdateUser(UserDomain model) {
        if (model == null) {
            throw new IllegalArgumentException("Tried to map null to User");
        }
        return new UpdateUser()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .phone(model.getPhone());
    }
}
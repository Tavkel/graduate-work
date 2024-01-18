package ru.skypro.homework.security.utility;

import ru.skypro.homework.models.OwnedEntity;
import ru.skypro.homework.models.domain.UserDomain;
import ru.skypro.homework.models.enums.Roles;

import java.util.Objects;

public class PermissionChecker {

    public static boolean isActionAllowed(UserDomain user, OwnedEntity entity) {
        return user.getUserRole() == Roles.ADMIN ||
                Objects.equals(user.getId(), entity.getOwnerId());
    }
}

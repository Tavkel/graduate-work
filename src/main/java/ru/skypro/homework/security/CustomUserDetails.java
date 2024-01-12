package ru.skypro.homework.security;

import org.springframework.security.core.userdetails.User;
import ru.skypro.homework.models.domain.UserDomain;

import java.util.List;

public class CustomUserDetails extends User {
    private final Integer id;
    private final UserDomain user;

    public CustomUserDetails(UserDomain user) {
        super(user.getEmail(), user.getPasswordHash(), List.of(user.getUserRole()));
        this.id = user.getId();
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public UserDomain getUser() {
        return user;
    }
}

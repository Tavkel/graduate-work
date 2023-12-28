package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.helpers.mappers.UserMapper;
import ru.skypro.homework.models.AuditableEntity;
import ru.skypro.homework.models.domain.UserDomain;
import ru.skypro.homework.models.dto.NewPassword;
import ru.skypro.homework.models.dto.UpdateUser;
import ru.skypro.homework.models.dto.User;
import ru.skypro.homework.models.enums.Roles;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.UserService;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser() {
        return UserMapper.userDomainToUser(getUserDomain());
    }

    @Override
    public UserDomain getUserDomain() {
        var principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }

    @Override
    public UpdateUser updateUser(UpdateUser user) {
        var model = repository.findById(getUserId()).orElseThrow(() -> {
            log.info("User with id {} was not found", 1);
            return new NoSuchElementException("User not found");
        });
        model.firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone());
        var result =repository.save(model);
        return UserMapper.userDomainToUpdateUser(result);
    }

    @Override
    public void updateUserPassword(NewPassword password) {
        var model = repository.findById(getUserId()).orElseThrow(() -> {
            log.info("User with id {} was not found", 1);
            return new NoSuchElementException("User not found");
        });
        model.passwordHash(passwordEncoder.encode(password.getNewPassword()));
        repository.save(model);
    }

    @Override
    public void updateAvatar(MultipartFile file) {
        //todo implement
    }

    @Override
    public Integer getUserId(){
        var user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @Override
    public boolean isActionAllowed(AuditableEntity entity){
        return Objects.equals(getUserId(), entity.getOwnerId()) ||
                getUser().getRole() == Roles.ADMIN;
    }
}

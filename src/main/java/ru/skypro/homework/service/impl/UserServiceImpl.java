package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.helpers.mappers.UserMapper;
import ru.skypro.homework.models.OwnedEntity;
import ru.skypro.homework.models.domain.UserDomain;
import ru.skypro.homework.models.dto.NewPassword;
import ru.skypro.homework.models.dto.UpdateUser;
import ru.skypro.homework.models.dto.User;
import ru.skypro.homework.models.enums.Roles;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.security.CustomUserDetails;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
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
        var model = getUserDomain();
        model.firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone());
        var result = repository.save(model);
        return UserMapper.userDomainToUpdateUser(result);
    }

    @Override
    public void updateUserPassword(NewPassword password) {
        var model = getUserDomain();
        model.passwordHash(passwordEncoder.encode(password.getNewPassword()));
        repository.save(model);
    }

    @Override
    public void updateAvatar(MultipartFile file) {
        var model = getUserDomain();
        try {
            var url = "/content/" + imageService.saveImage(file, model);
            model.setImageUrl(url);
        } catch (IOException e) {
            throw new RuntimeException(e); //todo specify exception
        }
        repository.save(model);
    }

    @Override
    public Integer getUserId() {
        var user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}

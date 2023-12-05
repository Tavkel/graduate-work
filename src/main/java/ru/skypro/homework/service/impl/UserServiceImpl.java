package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.helpers.mappers.UserMapper;
import ru.skypro.homework.models.domain.UserDomain;
import ru.skypro.homework.models.dto.NewPassword;
import ru.skypro.homework.models.dto.UpdateUser;
import ru.skypro.homework.models.dto.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.NoSuchElementException;

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
    public User getUser(Integer id) {
        return UserMapper.userDomainToUser(getUserDomain(id));
    }

    @Override
    public UserDomain getUserDomain(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.info("User with id {} was not found", id);
            return new NoSuchElementException("User not found");
        });
    }

    @Override
    public UpdateUser updateUser(UpdateUser user) {
        //todo remove stub
        var model = repository.findById(1).orElseThrow(() -> {
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
        //todo remove stub
        var model = repository.findById(1).orElseThrow(() -> {
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
}

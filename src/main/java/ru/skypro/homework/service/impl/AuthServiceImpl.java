package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.helpers.mappers.UserMapper;
import ru.skypro.homework.models.domain.UserDomain;
import ru.skypro.homework.models.dto.Register;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    public AuthServiceImpl(UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder,
                           UserRepository repository) {
        this.userDetailsService = userDetailsService;
        this.encoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (repository.existsByEmail(register.getUsername())) {
            return false;
        }

        UserDomain user = UserMapper.registerToUserDomain(register);
        user.setPasswordHash(encoder.encode(register.getPassword()));
        repository.save(user);
        return true;
    }
}

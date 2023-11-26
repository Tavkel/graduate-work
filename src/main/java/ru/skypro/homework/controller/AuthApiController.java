package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AuthApiController implements AuthApi{
    private final AuthService authService;

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public AuthApiController(AuthService authService, ObjectMapper objectMapper, HttpServletRequest request) {
        this.authService = authService;
        this.objectMapper = objectMapper;
        this.request = request;
    }
    @Override
    public ResponseEntity<Void> login(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema())
                                      @Valid
                                      @RequestBody Login login) {
        if (authService.login(login.getUsername(), login.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public ResponseEntity<Void> register(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema())
                                         @Valid
                                         @RequestBody Register register) {
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

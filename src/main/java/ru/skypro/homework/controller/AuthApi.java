package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.skypro.homework.models.dto.Login;
import ru.skypro.homework.models.dto.Register;

@Validated
public interface AuthApi {

    @Operation(summary = "Авторизация пользователя", description = "", tags = {"Авторизация"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),

            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/login",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> login(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                               @Valid
                               @RequestBody Login body);

    @Operation(summary = "Регистрация пользователя", description = "", tags = {"Регистрация"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),

            @ApiResponse(responseCode = "400", description = "Bad Request")})
    @RequestMapping(value = "/register",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> register(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                  @Valid
                                  @RequestBody Register body);
}
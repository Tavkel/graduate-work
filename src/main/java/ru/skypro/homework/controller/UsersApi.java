package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.dto.NewPassword;
import ru.skypro.homework.models.dto.UpdateUser;
import ru.skypro.homework.models.dto.User;

@Validated
public interface UsersApi {

    @Operation(summary = "Получение информации об авторизованном пользователе", description = "", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    ResponseEntity<User> getUser();


    @Operation(summary = "Обновление пароля", description = "", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),

            @ApiResponse(responseCode = "401", description = "Unauthorized"),

            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @RequestMapping(value = "/users/set_password",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> setPassword(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                     @Valid
                                     @RequestBody NewPassword body);


    @Operation(summary = "Обновление информации об авторизованном пользователе", description = "", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UpdateUser.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/users/me",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    ResponseEntity<UpdateUser> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                          @Valid
                                          @RequestBody UpdateUser body);


    @Operation(summary = "Обновление аватара авторизованного пользователя", description = "", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),

            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/users/me/image",
            consumes = {"multipart/form-data"},
            method = RequestMethod.PATCH)
    ResponseEntity<Void> updateUserImage(@Parameter(description = "file detail")
                                         @Valid
                                         @RequestPart("image") MultipartFile image);

}


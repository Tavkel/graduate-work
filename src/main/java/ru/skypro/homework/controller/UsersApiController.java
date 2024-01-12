package ru.skypro.homework.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.dto.UpdateUser;
import ru.skypro.homework.models.dto.User;
import ru.skypro.homework.models.dto.NewPassword;
import ru.skypro.homework.service.UserService;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
public class UsersApiController implements UsersApi {
    private final UserService userService;
    private final HttpServletRequest request;

    @Autowired
    public UsersApiController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @RequestMapping(value = "/users/me",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

    public ResponseEntity<Void> setPassword(@Valid @RequestBody NewPassword password) {
        userService.updateUserPassword(password);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<UpdateUser> updateUser(@Valid @RequestBody UpdateUser body) {
        return new ResponseEntity<>(userService.updateUser(body), HttpStatus.OK);
    }

    public ResponseEntity<Void> updateUserImage(@RequestPart MultipartFile image) {
        userService.updateAvatar(image);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

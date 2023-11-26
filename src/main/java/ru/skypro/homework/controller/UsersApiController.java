package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.NewPassword;

import java.io.IOException;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final HttpServletRequest request;

    @Autowired
    public UsersApiController(HttpServletRequest request) {
        this.request = request;
    }

    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }

    public ResponseEntity<Void> setPassword(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema())
                                            @Valid
                                            @RequestBody NewPassword body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UpdateUser> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema())
                                                 @Valid
                                                 @RequestBody UpdateUser body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateUserImage(@Parameter(description = "file detail")
                                                @Valid
                                                @RequestPart("file") MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}

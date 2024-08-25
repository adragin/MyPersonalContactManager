package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.exceptions.InvalidLoginPasswordException;
import com.example.MyPersonalContactManager.exceptions.UserAlreadyExistsException;
import com.example.MyPersonalContactManager.models.Error;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOResponse;
import com.example.MyPersonalContactManager.service.InterfaceUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    public final InterfaceUserService dbUserService;


    @PostMapping(value = "/userRegistration", consumes = "application/json")
    public ResponseEntity<UserDTOResponse> userRegistration(@Valid @RequestBody UserDTORegister user) {
        UserDTOResponse userResponse = dbUserService.registerUser(user);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<UserDTOResponse> userAuthorization(@Valid @RequestBody UserDTOLogin userDto) {
        UserDTOResponse userResponse = dbUserService.loginUser(userDto);
        return ResponseEntity.ok(userResponse);
    }

    //обработка исключения - проверка на существующего юзера
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserDTOResponse> handleUserExistException(UserAlreadyExistsException exception) {
        UserDTOResponse response = UserDTOResponse.builder()
                .error(new Error(500, "User already exists"))
                .build();
        return ResponseEntity.ok(response);
    }

    //обработка исключения - проверка пары логин-пароль
    @ExceptionHandler(InvalidLoginPasswordException.class)
    public ResponseEntity<UserDTOResponse> handlerCheckLoginPassException(InvalidLoginPasswordException exception) {
        UserDTOResponse response = UserDTOResponse.builder()
                .error(new Error(500, "Invalid login or password"))
                .build();
        return ResponseEntity.ok(response);
    }

}

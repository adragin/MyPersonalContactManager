package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.exceptions.UserAlreadyExistsException;
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

    @GetMapping("/authUser")
    public ResponseEntity<UserDTOResponse> userAuthorization(UserDTOLogin userDto) {
        return null;
    }

    //обработка исключения - проверка на существующего юзера
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserDTOResponse> handleException(UserAlreadyExistsException exception) {
        UserDTOResponse body = new UserDTOResponse(exception.getMessage());
        return ResponseEntity.ok(body);
    }

}

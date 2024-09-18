package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.exceptions.EasyUserPasswordException;
import com.example.MyPersonalContactManager.exceptions.InvalidLoginPasswordException;
import com.example.MyPersonalContactManager.exceptions.UserAlreadyExistsException;
import com.example.MyPersonalContactManager.models.Error;
import com.example.MyPersonalContactManager.models.RequestResponseModels.ResponseAPI;
import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOResponse;
import com.example.MyPersonalContactManager.service.InterfaceUserService;
import com.example.MyPersonalContactManager.utils.UtilsCheckToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User'ы", description = "Контроллер простой регистрации и авторизации пользователей (таблица users)")
public class UserController {

    @Autowired
    public final InterfaceUserService dbUserService;
    @Autowired
    private final ResponseAPI responseAPI;
    @Autowired
    private UtilsCheckToken utilsCheckToken;

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @Parameter(name = "password", description = "Для корректности ввода пароль должен содержать:\n" +
            " - только латиницу;\n - минимум 8 символов;\n - минимум 1 большую букву;\n - один из спец.сомволов: !@#$%^&*()-+=<?>")
    @PostMapping(value = "/userRegistration", consumes = "application/json")
    public ResponseEntity<ResponseAPI> userRegistration(@Valid @RequestBody UserDTORegister user) {
        UserDTOResponse userResponse = dbUserService.registerUser(user);
        return buildSuccessResponse(userResponse);
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @PostMapping("/userLogin")
    public ResponseEntity<ResponseAPI> userAuthorization(@Valid @RequestBody UserDTOLogin userDto) {
        UserDTOResponse userResponse = dbUserService.loginUser(userDto);
        return buildSuccessResponse(userResponse);
    }

    @Operation(summary = "Получить пользователя по id", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseAPI> getUserById(@PathVariable String userId,
                                                   @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            responseAPI.response = new Error(403, "Access denied.");
            return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
        }

        responseAPI.response = dbUserService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Изменить роль пользователя на ADMIN по id", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ResponseAPI> setAdminByUserId(@PathVariable String userId,
                                                        @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            return buildErrorResponse(403, "Access denied.");
        }

        User user = dbUserService.getUserById(userId);
        user.setRole(true);
        dbUserService.updateUser(user);
        responseAPI.response = user;

        return buildSuccessResponse(responseAPI);
    }


    @Operation(summary = "Получить список всех пользователей", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @GetMapping()
    public ResponseEntity<ResponseAPI> getAllUsers(@RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            responseAPI.response = new Error(403, "Access denied.");
            return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
        }
        List<User> users = dbUserService.getAllUsers();
        responseAPI.response = users;
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Удалить пользователя по id", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseAPI> deleteUserBtId(@PathVariable String userId,
                                                      @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            return buildErrorResponse(403, "Access denied.");
        }

        boolean isDeleted = dbUserService.deleteUserById(userId);
        if (!isDeleted) {
            return buildErrorResponse(404, "User not found.");
        }

        return buildSuccessResponse(true);
    }

    private ResponseEntity<ResponseAPI> buildSuccessResponse(Object response) {
        responseAPI.response = response;
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    private ResponseEntity<ResponseAPI> buildErrorResponse(int statusCode, String message) {
        responseAPI.response = new Error(statusCode, message);
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    //обработка исключений
    @ExceptionHandler({EasyUserPasswordException.class, InvalidLoginPasswordException.class, UserAlreadyExistsException.class})
    public ResponseEntity<UserDTOResponse> handlerCheckDataException(Exception exception) {
        UserDTOResponse response = UserDTOResponse.builder()
                .error(new Error(500, exception.getMessage()))
                .build();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserDTOResponse> handlerCheckLoginException(MethodArgumentNotValidException exception) {
        UserDTOResponse response = UserDTOResponse.builder()
                .error(new Error(500, "You login is too easy"))
                .build();
        return ResponseEntity.ok(response);
    }
}

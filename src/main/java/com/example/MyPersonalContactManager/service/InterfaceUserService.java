package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.UserModels.*;

import java.util.List;
import java.util.Optional;

public interface InterfaceUserService {

    UserDTOResponse registerUser(UserDTORegister userDTO);

    Optional<UserToken> loginUser(UserDTOLogin userDTO);

    User getUserById(String userId);

    List<User> getAllUsers();

    boolean deleteUserById(String userId);

    UserDTOResponse updateUser(User user);

    String generateToken(String login, String password);
}




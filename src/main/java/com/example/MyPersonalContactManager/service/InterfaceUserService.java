package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOResponse;

import java.util.List;

public interface InterfaceUserService {

    UserDTOResponse registerUser(UserDTORegister userDTO);

    UserDTOResponse loginUser(UserDTOLogin userDTO);

    User getUserById(String userId);

    List<User> getAllUsers();

    boolean deleteUserById(String userId);

    UserDTOResponse updateUser(User user);

    String generateToken(String login, String password);

    String getUserIdByToken(String token);

    boolean isAdmin(String token);
}




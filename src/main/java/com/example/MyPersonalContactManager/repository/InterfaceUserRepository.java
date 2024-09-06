package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.UserModels.User;

import java.util.List;

public interface InterfaceUserRepository {

    User createUser(User user);

    User getUserById(String userId);

    User getUserByLogin(String login);

    List<User> getAllUsers();

    String getUserIdByToken(String token);

    boolean getUserRoleByToken(String token);

    User updateUser(User user);

    User setUserRoleById(String userId, boolean role);

    boolean deleteUserById(String userId);

    String getToken(String userId);

    void saveToken(String token, String userId);
}


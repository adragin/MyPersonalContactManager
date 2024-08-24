package com.example.MyPersonalContactManager.repository;

import java.util.List;
import java.util.Optional;

public interface InterfaceUserRepository<T> {

    T createUser(T user);

    T getUserById(String userId);

    Optional<T> getUserByLogin(String login);

    List<T> getAllUsers();

    boolean deleteUserById(String userId);

    T updateUser(T user);

    void saveToken(String token, String userId);
}


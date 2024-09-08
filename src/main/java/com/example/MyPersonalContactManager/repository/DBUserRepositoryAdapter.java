package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class DBUserRepositoryAdapter implements InterfaceUserRepository {

    @Autowired
    private

    @Override
    public User createUser(User user) {
        return;
    }

    @Override
    public User getUserById(String userId) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public String getUserIdByToken(String token) {
        return "";
    }

    @Override
    public boolean getUserRoleByToken(String token) {
        return false;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User setUserRoleById(String userId, boolean role) {
        return null;
    }

    @Override
    public boolean deleteUserById(String userId) {
        return false;
    }

    @Override
    public String getToken(String userId) {
        return "";
    }

    @Override
    public void saveToken(String token, String userId) {

    }
}

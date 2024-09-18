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
    private InterfaceUserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public String getUserIdByToken(String token) {
        return userRepository.getUserIdByToken(token);
    }

    @Override
    public boolean getUserRoleByToken(String token) {
        return userRepository.getUserRoleByToken(token);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public User setUserRoleById(String userId, boolean role) {
        return userRepository.setUserRoleById(userId, role);
    }

    @Override
    public boolean deleteUserById(String userId) {
        return userRepository.deleteUserById(userId);
    }

    @Override
    public String getToken(String userId) {
        return userRepository.getToken(userId);
    }

    @Override
    public void saveToken(String token, String userId) {
        userRepository.saveToken(token, userId);
    }
}

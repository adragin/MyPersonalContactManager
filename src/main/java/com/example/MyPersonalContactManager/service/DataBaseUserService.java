package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.models.UserModels.*;
import com.example.MyPersonalContactManager.repository.InterfaceUserRepository;
import com.example.MyPersonalContactManager.utils.UtilsAuthorization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DataBaseUserService implements InterfaceUserService {

    private final InterfaceUserRepository<User> userRepository;
    private UtilsAuthorization utilsAuthorization;

    @Override
    public UserDTOResponse registerUser(UserDTORegister userDTORegister) {
        Optional<User> existingUser = userRepository.getUserByLogin(userDTORegister.getLogin());
        if (existingUser.isPresent()) {
            return null;
        }

        User newUser = new User();
        newUser.setLogin(userDTORegister.getLogin());
        newUser.setPassword(userDTORegister.getPassword());
        newUser.setUserName(userDTORegister.getName());
        newUser.setRole(false);

        userRepository.createUser(newUser);

        String token = utilsAuthorization.generateToken(newUser.getLogin(), newUser.getPassword());
        return new UserDTOResponse(newUser.getLogin(), token);
    }

    @Override
    public Optional<UserToken> loginUser(UserDTOLogin userDTOLogin) {
        Optional<User> existingUser = userRepository.getUserByLogin(userDTOLogin.getLogin());
        if (existingUser.isEmpty() || !existingUser.get().getPassword().equals(userDTOLogin.getPassword())) {
            throw new RuntimeException("Invalid login or password.");
        }
        String token = utilsAuthorization.generateToken(existingUser.get().getLogin(), existingUser.get().getPassword());
        return Optional.empty();
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public boolean deleteUserById(String userId) {
        return userRepository.deleteUserById(userId);
    }

    @Override
    public UserDTOResponse updateUser(User user) {
        return null;
    }

    @Override
    public String generateToken(String login, String password) {
        return utilsAuthorization.generateToken(login, password);
    }
}

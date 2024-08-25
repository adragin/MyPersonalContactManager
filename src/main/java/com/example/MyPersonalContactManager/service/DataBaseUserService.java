package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.exceptions.InvalidLoginPasswordException;
import com.example.MyPersonalContactManager.exceptions.UserAlreadyExistsException;
import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOResponse;
import com.example.MyPersonalContactManager.repository.InterfaceUserRepository;
import com.example.MyPersonalContactManager.utils.UtilsRegistration;
import com.example.MyPersonalContactManager.utils.UtilsUserAuthorization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataBaseUserService implements InterfaceUserService {

    private final InterfaceUserRepository<User> userRepository;
    private UtilsRegistration utilsRegistration;
    private UtilsUserAuthorization utilsUserAuth;

    @Override
    public UserDTOResponse registerUser(UserDTORegister userDTORegister) {
        User existingUser = userRepository.getUserByLogin(userDTORegister.getLogin());
        if (utilsRegistration.checkExistingUser(existingUser)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User newUser = new User();
        newUser.setLogin(userDTORegister.getLogin());
        newUser.setPassword(userDTORegister.getPassword());
        newUser.setUserName(userDTORegister.getName());
        newUser.setRole(false);

        userRepository.createUser(newUser);

        String token = utilsRegistration.generateToken(newUser.getLogin(), newUser.getPassword());
        userRepository.saveToken(token, String.valueOf(newUser.getUserId()));
        return new UserDTOResponse(newUser.getLogin(), token);
    }

    @Override
    public UserDTOResponse loginUser(UserDTOLogin userDTOLogin) {
        User existingUser = userRepository.getUserByLogin(userDTOLogin.getLogin());
        if (!utilsUserAuth.checkExistingUser(existingUser, userDTOLogin)) {
            throw new InvalidLoginPasswordException("Invalid login or password.");
        }
        User newUser = new User();
        newUser.setLogin(userDTOLogin.getLogin());

        String token = utilsRegistration.generateToken(userDTOLogin.getLogin(), userDTOLogin.getPassword());
        userRepository.saveToken(token, String.valueOf(newUser.getUserId()));
        return new UserDTOResponse(token);
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
        return utilsRegistration.generateToken(login, password);
    }
}

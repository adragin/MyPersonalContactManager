package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.exceptions.EasyUserPasswordException;
import com.example.MyPersonalContactManager.exceptions.InvalidLoginPasswordException;
import com.example.MyPersonalContactManager.exceptions.UserAlreadyExistsException;
import com.example.MyPersonalContactManager.models.UserModels.User;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOResponse;
import com.example.MyPersonalContactManager.repository.InterfaceUserRepository;
import com.example.MyPersonalContactManager.utils.UtilsRegistration;
import com.example.MyPersonalContactManager.utils.UtilsUserAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataBaseUserService implements InterfaceUserService {

    private final InterfaceUserRepository userRepository;
    private final UtilsRegistration utilsRegistration;
    private final UtilsUserAuthorization utilsUserAuth;

    @Autowired
    public DataBaseUserService(InterfaceUserRepository userRepository, UtilsRegistration utilsRegistration, UtilsUserAuthorization utilsUserAuth) {
        this.userRepository = userRepository;
        this.utilsRegistration = utilsRegistration;
        this.utilsUserAuth = utilsUserAuth;
    }

    @Override
    public UserDTOResponse registerUser(UserDTORegister userDTORegister) {
        User existingUser = userRepository.getUserByLogin(userDTORegister.getLogin());
        if (utilsRegistration.checkExistingUser(existingUser, userDTORegister)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (!utilsRegistration.checkCorrectPassword(userDTORegister.getPassword())) {
            throw new EasyUserPasswordException("Your password is too easy");
        }

        User newUser = new User();
        newUser.setLogin(userDTORegister.getLogin());
        newUser.setPassword(userDTORegister.getPassword());
        newUser.setUserName(userDTORegister.getName());
        newUser.setRole(false);

        userRepository.createUser(newUser);

        String token = generateToken(newUser.getLogin(), newUser.getPassword());
        userRepository.saveToken(token, String.valueOf(newUser.getUserId()));
        return UserDTOResponse.builder()
                .login(newUser.getLogin())
                .token(token)
                .build();
    }

    @Override
    public UserDTOResponse loginUser(UserDTOLogin userDTOLogin) {
        User existingUser = userRepository.getUserByLogin(userDTOLogin.getLogin());
        if (!utilsUserAuth.checkExistingUser(existingUser, userDTOLogin)) {
            throw new InvalidLoginPasswordException("Invalid login or password.");
        }

        String token = utilsRegistration.generateToken(userDTOLogin.getLogin(), userDTOLogin.getPassword());
        userRepository.saveToken(token, String.valueOf(existingUser.getUserId()));
        return UserDTOResponse.builder()
                .login(existingUser.getLogin())
                .token(token)
                .build();
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.getUserById(String.valueOf(userId));
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

    @Override
    public String getUserIdByToken(String token) {
        return userRepository.getUserIdByToken(token);
    }

    @Override
    public boolean getUserRoleByToken(String token) {
        return userRepository.getUserRoleByToken(token);
    }
}

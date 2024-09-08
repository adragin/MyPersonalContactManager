package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//@Primary
@Repository
public class DatabaseUserRepository implements InterfaceUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUserId((rs.getString("user_id")));
        user.setUserName(rs.getString("user_name"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getBoolean("user_role"));
        user.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        user.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return user;
    };

    @Override
    public User createUser(User user) {
        String insertSql = "INSERT INTO users (user_id, user_name, login, password, user_role, create_date, last_update_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                user.getUserId(),
                user.getUserName(),
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                user.getCreateDate().toLocalDate(),
                user.getLastUpdateDate().toLocalDate());

        return user;
    }

    @Override
    public User getUserById(String userId) {
        String selectSql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(selectSql, userRowMapper, userId);
    }

    @Override
    public User getUserByLogin(String login) {
        String selectSql = "SELECT * FROM users WHERE login = ?";
        try {
            return jdbcTemplate.queryForObject(selectSql, userRowMapper, login);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        String selectSql = "SELECT * FROM users";
        return jdbcTemplate.query(selectSql, userRowMapper);
    }

    @Override
    public boolean deleteUserById(String userId) {
        String deleteSql = "DELETE FROM users WHERE user_id = ?";
        return jdbcTemplate.update(deleteSql, userId) > 0;
    }

    @Override
    public User updateUser(User user) {
        String updateSql = "UPDATE users SET user_role = ?, login = ?, password = ?, user_name = ?, " +
                "last_update_date = ? WHERE user_id = ?";
        jdbcTemplate.update(updateSql,
                user.getRole(),
                user.getLogin(),
                user.getPassword(),
                user.getUserName(),
                user.getLastUpdateDate(),
                user.getUserId());
        return user;
    }

    @Override
    public User setUserRoleById(String userId, boolean role) {
        User user = getUserById(userId);
        user.setRole(role);
        return user;
    }

    @Override
    public String getToken(String userId) {
        String selectSql = "SELECT token FROM Users_Token WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(selectSql, new Object[]{userId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }

    @Override
    public void saveToken(String token, String userId) {
        if (getToken(userId).isEmpty()) {
            String insertTokenSql = "INSERT INTO users_token (token, user_id, create_date, last_update_date) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertTokenSql, token, userId, LocalDateTime.now(), LocalDateTime.now());
        } else {
            String updateTokenSql = "UPDATE users_token SET token = ?, last_update_date = ? WHERE user_id = ?";
            jdbcTemplate.update(updateTokenSql, token, LocalDateTime.now(), userId);
        }
    }

    public boolean getUserRoleByToken(String token) {
        String userId = getUserIdByToken(token);
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        String selectRole = "SELECT user_role FROM users WHERE user_id = ?";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(selectRole, new Object[]{userId}, Boolean.class));
    }

    @Override
    public String getUserIdByToken(String token) {
        String selectUserId = "SELECT user_id FROM users_token WHERE token = ?";
        try {
            return jdbcTemplate.queryForObject(selectUserId, new Object[]{token}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }


}


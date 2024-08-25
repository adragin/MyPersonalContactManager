package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DatabaseUserRepository implements InterfaceUserRepository<User> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUserId(UUID.fromString(rs.getString("user_id")));
        user.setRole(rs.getBoolean("USER_ROLE"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("USER_PASSWORD"));
        user.setUserName(rs.getString("user_name"));
        user.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        user.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return user;
    };

    @Override
    public User createUser(User user) {
        String insertSql = "INSERT INTO users (user_id, USER_ROLE, login, USER_PASSWORD, user_name, create_date, last_update_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                user.getUserId().toString(),
                user.getRole(),
                user.getLogin(),
                user.getPassword(),
                user.getUserName(),
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
    public Optional<User> getUserByLogin(String login) {
        String selectSql = "SELECT * FROM users WHERE login = ?";
        try {
            User user = jdbcTemplate.queryForObject(selectSql, userRowMapper, login);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
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
        String updateSql = "UPDATE users SET USER_ROLE = ?, login = ?, USER_PASSWORD = ?, user_name = ?, " +
                "last_update_date = ? WHERE user_id = ?";
        jdbcTemplate.update(updateSql,
                user.getRole(),
                user.getLogin(),
                user.getPassword(),
                user.getUserName(),
                user.getLastUpdateDate(),
                user.getUserId().toString());
        return user;
    }

    @Override
    public void saveToken(String token, String userId) {
        String insertTokenSql = "INSERT INTO User_Token (TOKEN, USER_ID, CREATE_DATE, LAST_UPDATE_DATE) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertTokenSql, token, userId, LocalDateTime.now(), LocalDateTime.now());
    }
}


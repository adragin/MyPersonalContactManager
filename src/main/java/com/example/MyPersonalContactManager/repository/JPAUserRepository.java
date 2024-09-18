package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JPAUserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userToken WHERE u.userId = :userId")
    User findUserWithToken(@Param("userId") String userId);
}

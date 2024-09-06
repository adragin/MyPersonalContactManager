package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.UserModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAUserRepository extends JpaRepository<User, String> {
}

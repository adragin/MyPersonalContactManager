package com.example.MyPersonalContactManager.models.UserModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users_token")
public class UserToken {
    private String token;
    @Id
    private String userId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}


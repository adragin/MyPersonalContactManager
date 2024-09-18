package com.example.MyPersonalContactManager.models.UserModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    @Id
    @Column(name = "token")
    private String token;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}


package com.example.MyPersonalContactManager.config;

import com.example.MyPersonalContactManager.repository.DatabaseContactRepository;
import com.example.MyPersonalContactManager.service.DatabaseContactService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    @Bean
    public DatabaseContactRepository dbRepository (JdbcTemplate jdbcTemplate) {
        return new DatabaseContactRepository(jdbcTemplate);
    }

    @Bean
    public DatabaseContactService dbService (DatabaseContactRepository dbRepository) {
        return new DatabaseContactService(dbRepository);
    }
}

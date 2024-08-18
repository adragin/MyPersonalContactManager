package com.example.MyPersonalContactManager.Config;

import com.example.MyPersonalContactManager.repository.DatabaseContactRepository;
import com.example.MyPersonalContactManager.service.DatabaseContactService;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.ConstructorProperties;

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

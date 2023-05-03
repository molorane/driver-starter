package com.lifehacker.driverstarter.database;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class CreateTables {

    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(CreateTables.class);

    @PostConstruct
    public void init() {
        createTable(jdbcTemplate);
    }

    private void createTable(final JdbcTemplate jdbcTemplate) {
        logger.info("Attempting to create a table gdpuser..");
        final String SQL = """
                CREATE TABLE IF NOT EXISTS gdpuser(
                id INT AUTO_INCREMENT PRIMARY KEY, 
                name VARCHAR(255) NOT NULL, 
                age INT NOT NULL)""";
        jdbcTemplate.execute(SQL);
        logger.info("Creating a table gdpuser complete..");
    }
}

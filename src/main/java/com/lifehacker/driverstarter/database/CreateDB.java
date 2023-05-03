package com.lifehacker.driverstarter.database;

import com.lifehacker.driverstarter.properties.DatabaseProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.PostConstruct;

@AllArgsConstructor
@Data
public class CreateDB {

    private final Logger logger = LoggerFactory.getLogger(CreateDB.class);


    private JdbcTemplate gdpTemplate;
    private DatabaseProperties databaseProperties;

    @PostConstruct
    public void init() {
        logger.info("Attempting to create a DB..");
        createDB();
        logger.info("Creating a DB complete..");
    }

    private void createDB() {
        final String SQL = String.format("CREATE DATABASE IF NOT EXISTS %s", databaseProperties.getDatabase());
        gdpTemplate.execute(SQL);
    }
}

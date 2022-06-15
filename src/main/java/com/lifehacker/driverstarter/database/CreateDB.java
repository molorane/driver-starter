package com.lifehacker.driverstarter.database;

import com.lifehacker.driverstarter.properties.DatabaseProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@AllArgsConstructor
@Data
public class CreateDB {

    private final Logger logger = Logger.getLogger(String.valueOf(CreateDB.class));

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

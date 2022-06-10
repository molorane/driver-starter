package com.lifehacker.driverstarter.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
@Data
public class GdpDBDriver {

    private final Logger logger = Logger.getLogger(String.valueOf(GdpDBDriver.class));

    private JdbcTemplate jdbcTemplate;
    private Environment env;

    @PostConstruct
    public void init() {
        logger.info("Attempting to create a DB..");
        createDB();
        logger.info("Creating a DB complete..");
    }

    private void createDB() {
        final String SQL = String.format("CREATE DATABASE IF NOT EXISTS %s", env.getProperty("gdp.con.database"));
        jdbcTemplate.execute(SQL);
    }
}

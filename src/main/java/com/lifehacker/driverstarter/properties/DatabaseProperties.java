package com.lifehacker.driverstarter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("gdp.con")
public class DatabaseProperties {

    /**
     * Database name
     */
    private String database;

    /**
     * Datasource of url
     */
    private String url;

    /**
     * Datasource of user
     */
    private String user;

    /**
     * Datasource of password
     */
    private String password;
}

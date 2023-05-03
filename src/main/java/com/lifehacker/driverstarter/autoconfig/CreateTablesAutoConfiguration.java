package com.lifehacker.driverstarter.autoconfig;

import com.lifehacker.driverstarter.database.CreateDB;
import com.lifehacker.driverstarter.database.CreateTables;
import com.lifehacker.driverstarter.properties.DatabaseProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Optional;
import java.util.ServiceLoader;

@Configuration
@AllArgsConstructor
@ConditionalOnClass({Driver.class})
@ConditionalOnResource(resources = "classpath:data/users.csv")
@ConditionalOnProperty(prefix = "javaspace.connection", name = {"url", "user", "password", "database"})
@AutoConfigureAfter(CreateDBAutoConfiguration.class)
@ConditionalOnBean(CreateDB.class)
public class CreateTablesAutoConfiguration {

    private JdbcTemplate gdpTemplate;
    private DatabaseProperties databaseProperties;

    @Bean
    @ConditionalOnMissingBean
    public CreateTables createTables() {
        gdpTemplate.setDataSource(dataSourceForDDL());
        return new CreateTables(gdpTemplate);
    }

    @Bean
    public DataSource dataSourceForDDL() {
        final Optional<Driver> serviceLoader = ServiceLoader.load(Driver.class).findFirst();
        final Driver driver = serviceLoader.orElseThrow(() -> new RuntimeException("Driver not found"));
        final String url = databaseProperties.getUrl() + databaseProperties.getDatabase();
        final String user = databaseProperties.getUser();
        final String password = databaseProperties.getPassword();
        return new SimpleDriverDataSource(driver, url, user, password);
    }
}

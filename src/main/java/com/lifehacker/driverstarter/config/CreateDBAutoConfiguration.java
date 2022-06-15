package com.lifehacker.driverstarter.config;

import com.lifehacker.driverstarter.conditions.MySQLDriverOnClassPathCondition;
import com.lifehacker.driverstarter.database.CreateDB;
import com.lifehacker.driverstarter.properties.DatabaseProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Optional;
import java.util.ServiceLoader;

@ConditionalOnClass(Driver.class)
@Conditional(MySQLDriverOnClassPathCondition.class)
@ConditionalOnProperty(prefix = "gdp.con", name = {"url", "user", "password", "database"})
@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
@AllArgsConstructor
public class CreateDBAutoConfiguration {

    private DatabaseProperties databaseProperties;

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        Optional<Driver> serviceLoader = ServiceLoader.load(Driver.class).findFirst();
        Driver driver = serviceLoader.get();
        String url = databaseProperties.getUrl();
        String user = databaseProperties.getUser();
        String password = databaseProperties.getPassword();
        return new SimpleDriverDataSource(driver, url, user, password);
    }

    @Bean
    public JdbcTemplate gdpTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CreateDB gdpDBDriver() {
        return new CreateDB(gdpTemplate(), databaseProperties);
    }
}

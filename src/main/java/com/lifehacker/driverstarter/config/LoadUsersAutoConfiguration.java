package com.lifehacker.driverstarter.config;

import com.lifehacker.driverstarter.database.GdpDBDriver;
import com.lifehacker.driverstarter.database.LoadUser;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.ServiceLoader;

@Configuration
@AllArgsConstructor
@ConditionalOnClass(Driver.class)
@ConditionalOnResource(resources = "classpath:data/users.csv")
@ConditionalOnProperty(prefix = "gdp.con", name = {"url", "user", "password", "database"})
@AutoConfigureAfter(GdpDataSourceAutoConfiguration.class)
@ConditionalOnBean(GdpDBDriver.class)
public class LoadUsersAutoConfiguration {

    private GdpDBDriver gdpDBDriver;

    @Bean
    @ConditionalOnMissingBean
    public LoadUser loadUser() {
        gdpDBDriver.getJdbcTemplate().setDataSource(database());
        return new LoadUser(gdpDBDriver.getJdbcTemplate());
    }

    @Bean
    public DataSource database() {
        Driver driver = ServiceLoader.load(Driver.class).findFirst().get();
        String url = gdpDBDriver.getEnv().getProperty("gdp.con.url") + gdpDBDriver.getEnv().getProperty("gdp.con.database");
        String user = gdpDBDriver.getEnv().getProperty("gdp.con.user");
        String password = gdpDBDriver.getEnv().getProperty("gdp.con.password");
        return new SimpleDriverDataSource(driver, url, user, password);
    }
}

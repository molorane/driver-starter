package com.lifehacker.driverstarter.config;

import com.lifehacker.driverstarter.conditions.MySQLDriverOnClassPathCondition;
import com.lifehacker.driverstarter.database.GdpDBDriver;
import com.lifehacker.driverstarter.properties.DatabaseProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.ServiceLoader;

@ConditionalOnClass(Driver.class)
@EnableConfigurationProperties(DatabaseProperties.class)
@Conditional(MySQLDriverOnClassPathCondition.class)
@ConditionalOnProperty(prefix = "gdp.con", name = {"url", "user", "password", "database"})
@Configuration
@AllArgsConstructor
public class GdpDataSourceAutoConfiguration {

    private Environment env;

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        Driver driver = ServiceLoader.load(Driver.class).findFirst().get();
        String url = env.getProperty("gdp.con.url");
        String user = env.getProperty("gdp.con.user");
        String password = env.getProperty("gdp.con.password");
        return new SimpleDriverDataSource(driver, url, user, password);
    }

    @Bean
    @ConditionalOnMissingBean
    public GdpDBDriver dbStarter() {
        return new GdpDBDriver(
                new JdbcTemplate(dataSource()),
                env
        );
    }
}

package com.lifehacker.driverstarter.config;

import com.lifehacker.driverstarter.database.CreateTables;
import com.lifehacker.driverstarter.database.LoadData;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Driver;

@Configuration
@AllArgsConstructor
@ConditionalOnClass({Driver.class})
@ConditionalOnResource(resources = "classpath:data/users.csv")
@AutoConfigureAfter(CreateTablesAutoConfiguration.class)
@ConditionalOnBean(CreateTables.class)
public class LoadDataAutoConfiguration {

    private JdbcTemplate gdpTemplate;

    @Bean
    @ConditionalOnMissingBean
    public LoadData loadData() {
        return new LoadData(gdpTemplate);
    }

}

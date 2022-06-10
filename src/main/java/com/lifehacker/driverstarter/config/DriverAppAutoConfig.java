package com.lifehacker.driverstarter.config;

import com.lifehacker.driverstarter.conditions.ConditionalOnValidCondition;
import com.lifehacker.driverstarter.conditions.DriverOnClassPathCondition;
import com.lifehacker.driverstarter.properties.DriverProperties;
import com.lifehacker.driverstarter.service.BDriver;
import com.lifehacker.driverstarter.service.DefaultDriver;
import com.lifehacker.driverstarter.service.DriverService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(DriverService.class)
@EnableConfigurationProperties(DriverProperties.class)
public class DriverAppAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnValidCondition
    public DriverService driverService(DriverProperties driverProperties) {
        return new DefaultDriver(
                driverProperties.getName(),
                driverProperties.getExperience()
        );
    }

    @Bean
    @Conditional(DriverOnClassPathCondition.class)
    public BDriver bDriver() {
        return new BDriver();
    }
}

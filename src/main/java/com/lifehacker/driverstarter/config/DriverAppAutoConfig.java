package com.lifehacker.driverstarter.config;

import com.lifehacker.driverstarter.conditions.ConditionalOnValidCondition;
import com.lifehacker.driverstarter.properties.DriverProperties;
import com.lifehacker.driverstarter.service.DefaultDriver;
import com.lifehacker.driverstarter.service.DriverService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

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

    @ConditionalOnJava(value = JavaVersion.NINE, range = ConditionalOnJava.Range.OLDER_THAN)
    public class RecommendedJava {

        private static final Logger logger = Logger.getLogger(String.valueOf(RecommendedJava.class));

        @Bean
        public DriverAdvanced driverAdvanced() {
            return new DriverAdvanced();
        }

        public static class DriverAdvanced implements DriverService {

            @PostConstruct
            public void init() {
                drive();
            }

            @Override
            public void drive() {
                logger.info("Java 9+ is recommended for security");
            }
        }
    }
}

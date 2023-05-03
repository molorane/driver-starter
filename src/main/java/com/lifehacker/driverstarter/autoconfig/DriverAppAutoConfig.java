package com.lifehacker.driverstarter.autoconfig;

import com.lifehacker.driverstarter.conditions.ConditionalOnValidCondition;
import com.lifehacker.driverstarter.properties.DriverProperties;
import com.lifehacker.driverstarter.service.DefaultDriver;
import com.lifehacker.driverstarter.service.DriverService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.logging.Logger;

@Configuration
@ConditionalOnClass(DriverService.class)
@EnableConfigurationProperties(DriverProperties.class)
public class DriverAppAutoConfig {

    @Bean
    @ConditionalOnValidCondition
    @ConditionalOnMissingBean
    public DriverService driverService(DriverProperties driverProperties) {
        return new DefaultDriver(
                driverProperties.getName(),
                driverProperties.getExperience()
        );
    }

    //@ConditionalOnJava(value = JavaVersion.NINE, range = ConditionalOnJava.Range.OLDER_THAN)
    public static class RecommendedJava {

        private static final Logger logger = Logger.getLogger(String.valueOf(RecommendedJava.class));

        @Bean
        public DriverAdvanced driverAdvanced() {
            return new DriverAdvanced();
        }

        public static class DriverAdvanced {

            @PostConstruct
            public void init() {
                logger.info("Java 9+ is recommended for security");
            }
        }
    }
}

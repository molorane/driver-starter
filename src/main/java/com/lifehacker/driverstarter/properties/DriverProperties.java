package com.lifehacker.driverstarter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("javaspace.developer")
public class DriverProperties {

    /**
     * Name of driver
     */
    private String name;

    /**
     * Experience of driver
     */
    private String experience;
}

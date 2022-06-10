package com.lifehacker.driverstarter.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.sql.Driver;
import java.util.ServiceLoader;

public class DriverOnClassPathCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        return ServiceLoader.load(Driver.class)
                .stream()
                .anyMatch(service -> service.get().toString().contains("mysql"));
    }
}

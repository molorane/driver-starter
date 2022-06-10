package com.lifehacker.driverstarter.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MySQLDriverOnClassPathCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}

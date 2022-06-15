package com.lifehacker.driverstarter.conditions;

import com.lifehacker.driverstarter.service.DriverService;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OwnConditionalOnMissingBean implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        try {
            context.getBeanFactory().getBean(DriverService.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

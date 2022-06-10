package com.lifehacker.driverstarter.conditions;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnDatabasePropertiesSet extends SpringBootCondition {

    private static final String PROPERTY_NAME = "driver.name";
    private static final String PROPERTY_LAST_NAME = "driver.experience";

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder condition = ConditionMessage.forCondition(ConditionalOnValidCondition.class);

        Environment environment = context.getEnvironment();

        if (environment.containsProperty(PROPERTY_NAME)) {
            final String blackList = environment.getProperty(PROPERTY_NAME);

            if (blackList.equals("Nomfundo")) {
                return ConditionOutcome.noMatch(
                        condition.because(String.format("Name %s is black listed", blackList)));
            }
        } else {
            return ConditionOutcome.noMatch(
                    condition.because("First name property missing!!"));
        }

        return ConditionOutcome.match();
    }
}

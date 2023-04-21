package com.lifehacker.driverstarter.conditions;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnValidCondition extends SpringBootCondition {

    private static final String PROPERTY_NAME = "javaspace.developer.name";
    private static final String PROPERTY_EXPERIENCE = "javaspace.developer.experience";

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
                    condition.because("First name property missing."));
        }

        if (!environment.containsProperty(PROPERTY_EXPERIENCE)) {
            return ConditionOutcome.noMatch(
                    condition.because("Experience property missing."));
        }

        return ConditionOutcome.match();
    }
}

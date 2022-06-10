package com.lifehacker.driverstarter.conditions;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnValidCondition.class)
public @interface ConditionalOnValidCondition {
}

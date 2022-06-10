package com.lifehacker.driverstarter.service;

import lombok.AllArgsConstructor;

import javax.annotation.PostConstruct;

@AllArgsConstructor
public class DefaultDriver implements DriverService {

    private String name;
    private String experience;

    @Override
    public void drive() {
        System.out.println(String.format("%s %s", name, experience));
    }

    @PostConstruct
    public void act(){
        drive();
    }
}

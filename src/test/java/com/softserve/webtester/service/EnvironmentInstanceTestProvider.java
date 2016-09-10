package com.softserve.webtester.service;

import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.EnvironmentDbType;

public class EnvironmentInstanceTestProvider {

    public static Environment getEnvironment(String name) {
        Environment environment = new Environment();
        environment.setName(name);
        return environment;
    }

    public static Environment getEnvironment(Integer id) {
        Environment environment = new Environment();
        environment.setId(id);
        return environment;
    }

    public static Environment getEnvironment() {
        Environment environment = new Environment();
        environment.setDbType(EnvironmentDbType.MYSQL);
        environment.setDbUrl("http://localohost");
        environment.setDbPort(3036);
        environment.setDbName("DbName");
        environment.setDbUsername("UserName");
        environment.setDbPassword("Password");
        return environment;
    }
}

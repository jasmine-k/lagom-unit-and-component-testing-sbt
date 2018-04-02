package com.example.hello.user.impl;

import com.example.hello.user.api.UserService;
import com.example.hello.user.external.api.ExternalService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import play.Configuration;
import play.Environment;

public class UserModule extends AbstractModule implements ServiceGuiceSupport {

    private final Environment environment;
    private final Configuration configuration;  //NOSONAR as this is required field.

    public UserModule(Environment environment, Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bindService(UserService.class, UserServiceImpl.class);
        bindClient(ExternalService.class);

    }

}

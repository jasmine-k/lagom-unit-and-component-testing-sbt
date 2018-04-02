package com.example.hello.user.impl;

import akka.NotUsed;
import com.example.hello.user.api.UserService;
import com.example.hello.user.external.api.ExternalService;
import com.example.hello.user.mapper.ResponseMapper;
import com.example.hello.user.external.models.UserData;
import com.example.hello.user.models.UserResponse;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.concurrent.CompletionStage;

public class UserServiceImpl implements UserService {

    private ExternalService externalService;

    private ResponseMapper responseMapper;

    @Inject
    public UserServiceImpl(ExternalService externalService, ResponseMapper responseMapper) {
        this.externalService = externalService;
        this.responseMapper = responseMapper;
    }

    private CompletionStage<UserData> hitAPI() {
        return externalService.getUser().invoke();
    }

    @Override
    public ServiceCall<NotUsed, UserResponse> helloUser() {
        return request ->
        {
            CompletionStage<UserData> userData = hitAPI();
            return userData.thenApply(
                    responseMapper::getResponse
            );
        };
    }
}

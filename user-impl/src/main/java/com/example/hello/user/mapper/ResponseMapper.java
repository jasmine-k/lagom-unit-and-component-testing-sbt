package com.example.hello.user.mapper;

import com.example.hello.user.external.models.UserData;
import com.example.hello.user.models.UserResponse;

public class ResponseMapper {

    public UserResponse getResponse(UserData userData) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage("Hello! Your UserId is " + userData.userId);
        return userResponse;
    }
}

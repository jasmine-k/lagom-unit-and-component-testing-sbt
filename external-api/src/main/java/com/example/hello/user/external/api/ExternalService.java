package com.example.hello.user.external.api;

import akka.NotUsed;
import com.example.hello.user.external.models.UserData;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

public interface ExternalService extends Service {

    ServiceCall<NotUsed, UserData> getUser();

    @Override
    default Descriptor descriptor() {
        return Service.named("external-service").withCalls(
                Service.restCall(Method.GET, "/posts/1", this::getUser)

        ).withAutoAcl(true);
    }

}

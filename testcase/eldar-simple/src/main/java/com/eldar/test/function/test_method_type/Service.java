package com.eldar.test.function.test_method_type;

import com.example.eldar.annotation.Consumer;
import com.example.eldar.annotation.Handler;
import com.example.eldar.entity.RequestContext;
import io.vertx.core.json.JsonObject;

@Handler
public class Service {

    @Consumer("testMethodType:handler1")
    public JsonObject handler1(RequestContext requestContext) {
        return JsonObject.mapFrom(requestContext);
    }

    @Consumer("testMethodType:handler2")
    public JsonObject handler2() {
        return new JsonObject().put("methodType", "post");
    }

    @Consumer("testMethodType:handler3")
    public JsonObject handler3() {
        return new JsonObject().put("methodType", "put");
    }

    @Consumer("testMethodType:handler4")
    public JsonObject handler4() {
        return new JsonObject().put("methodType", "delete");
    }

    @Consumer("testMethodType:handler5")
    public JsonObject handler5(RequestContext requestContext) {
        return JsonObject.mapFrom(requestContext);
    }
}

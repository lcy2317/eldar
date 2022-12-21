package com.eldar.test.function.test_method_type;

import com.example.eldar.annotation.Api;
import com.example.eldar.annotation.MethodType;
import com.example.eldar.annotation.Path;
import com.example.eldar.annotation.Sender;
import io.vertx.core.json.JsonObject;

@Api("/testMethodType")
public interface Controller {

    @Path("/get")
    @Sender("testMethodType:handler1")
    JsonObject getObject();

    @Path(value = "/post", methodType = MethodType.POST)
    @Sender("testMethodType:handler2")
    JsonObject postValue();

    @Path(value = "/put", methodType = MethodType.PUT)
    @Sender("testMethodType:handler3")
    JsonObject putValue();

    @Path(value = "/delete", methodType = MethodType.DELETE)
    @Sender("testMethodType:handler4")
    JsonObject deleteValue();


    @Path(value = "/put", methodType = MethodType.POST)
    @Sender("testMethodType:handler5")
    JsonObject getObject1();
}

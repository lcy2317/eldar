package com.eldar.test.function.posttest;

import com.eldar.test.function.Student;
import com.example.eldar.annotation.*;
import io.vertx.core.json.JsonObject;

@Api("/post")
public interface PostController {

    @Path(value = "/0", methodType = MethodType.POST)
    @Sender("handler0")
    JsonObject postValue();

    @Path(value = "/1", methodType = MethodType.POST)
    @Sender("handler1")
    JsonObject postValue1(@RequestParam String name, @RequestParam int age);

    @Path(value = "/2", methodType = MethodType.POST)
    @Sender("handler2")
    JsonObject postValue2(@RequestParam Student student);

    @Path(value = "/3", methodType = MethodType.POST)
    @Sender("handler3")
    JsonObject postValue3(@RequestParam String id, @RequestBody Student student);

    @Path(value = "/4/:id", methodType = MethodType.POST)
    @Sender("handler4")
    JsonObject postValue4(@PathVariable String id);


}

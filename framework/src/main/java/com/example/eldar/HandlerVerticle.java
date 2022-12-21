package com.example.eldar;

import com.example.eldar.annotation.Consumer;
import com.example.eldar.annotation.Handler;
import com.example.eldar.entity.RequestContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

import java.lang.reflect.Method;


public class HandlerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        ApplicationContext.getClassPool().forEach(e -> {
            if (e.getAnnotation(Handler.class) != null) {
                try {
                    var cons = e.getDeclaredConstructor();
                    var ins = cons.newInstance();

                    var methods = e.getDeclaredMethods();
                    for (Method method : methods) {
                        var address = method.getAnnotation(Consumer.class).value();
                        vertx.eventBus().consumer(address, (Message<RequestContext> message) -> {
                            try {
                                message.reply(method.invoke(ins, adaptParams(method, message.body())));
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private Object[] adaptParams(Method consumer, RequestContext context) {
        var count = consumer.getParameterCount();
        if (count == 0) {
            return new Object[0];
        }
        return new Object[]{context};
    }

}

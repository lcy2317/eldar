package com.example.eldar;

import com.example.eldar.annotation.Api;
import com.example.eldar.annotation.MethodType;
import com.example.eldar.annotation.Path;
import com.example.eldar.annotation.Sender;
import com.example.eldar.entity.RequestContext;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class HttpServer extends AbstractVerticle {

    @Override
    public void start() {
        var server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        var apis = ApplicationContext.filter(ApplicationContext.getApis);
        apis.forEach(el -> registerApiRouter(el, router));
        server.requestHandler(router).listen(8080);
    }

    private void registerApiRouter(Class<?> classes, Router router) {
        var apiAnnotation = classes.getAnnotation(Api.class);
        var methods = classes.getDeclaredMethods();

        for (Method method : methods) {
            var annoPath = method.getAnnotation(Path.class);
            var pathValue = annoPath.value();

            var route = switchByMethodType(router, annoPath.methodType());

            route.path(apiAnnotation.value() + pathValue);
            System.out.println(annoPath.methodType().toString() + " " + apiAnnotation.value() + pathValue);
            route.handler(ctx -> {

                var res = ctx.response();
                res.headers().set("Content-Type", "application/json");

                var address = method.getAnnotation(Sender.class).value();


                buildParams(ctx.request(), requestContext -> {

                    vertx.eventBus().request(address, requestContext, ar -> {

                        if (ar.succeeded()) {
                            res.end(ar.result().body().toString());
                        } else {
                            res.end("error");
                        }
                    });
                });
            });
        }
    }

    private Route switchByMethodType(Router router, MethodType type) {
        return switch (type) {
            case GET -> router.get();
            case POST -> router.post();
            case PUT -> router.put();
            case DELETE -> router.delete();
        };
    }

    private void buildParams(HttpServerRequest request, Consumer<RequestContext> consumer) {
        var requestContext = new RequestContext();

        requestContext.addRequestHeaders(request.headers().entries());
        requestContext.addRequestParams(request.params().entries());


        request.bodyHandler(h -> {
            if (!StringUtil.isNullOrEmpty(h.toString())) {
                requestContext.addRequestBody(h.toJsonObject());
            }
            consumer.accept(requestContext);
        });
    }

}

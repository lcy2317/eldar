package com.example.eldar;

import com.example.eldar.util.ClassUtil;
import com.example.eldar.util.Scanner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Framework {

    public static void run(Class<?> clazz) throws Exception {
        var set = Scanner.defaultScan();
        set.addAll(Scanner.scan(clazz));
        ApplicationContext.putAllClassesToPool(set.stream().map(ClassUtil::forName).collect(Collectors.toSet()));

        var vertx = Vertx.vertx();

        ApplicationContext.getClassPool().forEach(e -> {
            try {
                if (AbstractVerticle.class.isAssignableFrom(e)) {
                    vertx.deployVerticle((Class<? extends Verticle>) e, new DeploymentOptions());
                    System.out.println("deploy verticle:" + e.getName());
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });
    }
}

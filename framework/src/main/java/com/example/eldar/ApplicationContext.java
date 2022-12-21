package com.example.eldar;

import com.example.eldar.annotation.Api;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ApplicationContext {

    private static final ConcurrentHashMap<String, Class<?>> classPool = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Object> objectPool = new ConcurrentHashMap<>();


    private ApplicationContext() {

    }

    public static void putClassToPool(String name, Class<?> clazz) {
        classPool.put(name, clazz);
    }

    public static void putAllClassesToPool(Map<String, Class<?>> classes) {
        classPool.putAll(classes);
    }

    public static void putAllClassesToPool(Set<Class<?>> classes) {
        classPool.putAll(classes.stream()
                .collect(Collectors.toMap(Class::getName, Function.identity())));
    }

    public static Class<?> filter(String name) {
        return classPool.get(name);
    }

    public static Set<Class<?>> filter(Predicate<Class<?>> predicate) {
        return getClassPool().stream().filter(predicate).collect(Collectors.toSet());
    }

    public static Set<Class<?>> getClassPool() {
        return new HashSet<>(classPool.values());
    }

    public static Predicate<Class<?>> getApis = cls -> cls.getAnnotation(Api.class) != null;

}

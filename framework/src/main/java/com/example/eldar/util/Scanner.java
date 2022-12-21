package com.example.eldar.util;


import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class Scanner {

    private static final String BASE_PACKAGE_NAME = "com.example.eldar";

    public static Set<String> defaultScan() throws IOException {
        return scan(BASE_PACKAGE_NAME);
    }

    public static Set<String> scan(Class<?> clazz) throws IOException {
        return scan(clazz.getPackageName());
    }

    public static Set<String> scan(String basePackage) throws IOException {
        final ClassPath cp = ClassPath.from(Thread.currentThread().getContextClassLoader());
        final ImmutableSet<ClassPath.ClassInfo> set = cp.getTopLevelClassesRecursive(basePackage);
        return set.stream().map(ClassPath.ClassInfo::getName).collect(Collectors.toSet());
    }

}

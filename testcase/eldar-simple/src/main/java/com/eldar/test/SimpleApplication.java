package com.eldar.test;

import com.example.eldar.Framework;
import com.example.eldar.annotation.Application;

@Application
public class SimpleApplication {

    public static void main(String[] args) throws Exception {
        Framework.run(SimpleApplication.class);

    }

}

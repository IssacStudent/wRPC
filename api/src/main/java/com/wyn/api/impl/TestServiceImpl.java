package com.wyn.api.impl;

import com.wyn.api.TestServiceApi;

public class TestServiceImpl implements TestServiceApi {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}

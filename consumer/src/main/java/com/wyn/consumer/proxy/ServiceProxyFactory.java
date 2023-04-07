package com.wyn.consumer.proxy;

import java.lang.reflect.Proxy;

public class ServiceProxyFactory {
    public static <T> T getInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new ServiceProxy(clazz)
        );
    }
}

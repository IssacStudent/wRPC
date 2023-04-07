package com.wyn.demo;

import com.wyn.api.TestServiceApi;
import com.wyn.consumer.proxy.ServiceProxyFactory;

public class ConsumerDemo {
    public static void main(String[] args) {
        TestServiceApi testServiceApi = ServiceProxyFactory.getInstance(TestServiceApi.class);
        testServiceApi.printHello("Isaac Student");
    }
}

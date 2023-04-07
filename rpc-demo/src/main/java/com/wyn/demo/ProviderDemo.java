package com.wyn.demo;

import com.wyn.api.TestServiceApi;
import com.wyn.api.impl.TestServiceImpl;
import com.wyn.provider.invoker.DefaultInvoker;
import com.wyn.provider.server.DefaultServerRemoter;

import java.io.IOException;

public class ProviderDemo {
    public static void main(String[] args) throws IOException {
        DefaultInvoker proxy = DefaultInvoker.PROXY;
        proxy.publish(TestServiceApi.class, new TestServiceImpl());
        DefaultServerRemoter remoter = new DefaultServerRemoter();
        remoter.startServer(9999);
    }
}

package com.wyn.demo;

import com.wyn.api.TestServiceApi;
import com.wyn.api.impl.TestServiceImpl;
import com.wyn.provider.executors.ServerExecutor;
import com.wyn.provider.invoker.ServiceInvoker;
import com.wyn.provider.server.ServerRemoter;

import java.io.IOException;

public class ProvicerDemo {
    public static void main(String[] args) throws IOException {
        ServiceInvoker proxy = ServiceInvoker.PROXY;
        proxy.publish(TestServiceApi.class, new TestServiceImpl());
        ServerRemoter remoter = new ServerRemoter();
        remoter.startServer(9999);
    }
}

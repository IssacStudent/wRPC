package com.wyn.provider.server;

import com.wyn.provider.executors.ServerExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ServerRemoter {

    private ExecutorService executorService;

    private Boolean isShutDown;

    public ServerRemoter() {
        isShutDown = false;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public ServerRemoter(ExecutorService executorService) {
        isShutDown = false;
        this.executorService = executorService;
    }

    public void startServer(int port) throws IOException {
        isShutDown = false;
        final ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        log.info("-------------start server----------------");
        try {
            while (!isShutDown) {
                final Socket socket = serverSocket.accept();
                executorService.execute(new ServerExecutor(socket));
            }
        } finally {
            serverSocket.close();
        }
    }

    public Boolean getShutDown() {
        return isShutDown;
    }

    public void setShutDown(Boolean shutDown) {
        isShutDown = shutDown;
    }

}

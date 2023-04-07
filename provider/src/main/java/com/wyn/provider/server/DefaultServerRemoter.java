package com.wyn.provider.server;

import com.wyn.common.interfaces.communication.Server;
import com.wyn.provider.executors.ServerExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端通信组件，接收客户端请求，并交给执行器Executor处理
 * @author Yining Wang
 * @date 2023年4月7日11:31:26
 * @since <pre>2023/04/07</pre>
 */
@Slf4j
public class DefaultServerRemoter implements Server {

    /**
     * 线程池，可自定义
     */
    private final ExecutorService executorService;

    /**
     * 关闭服务端
     */
    private Boolean isShutDown;

    public DefaultServerRemoter() {
        isShutDown = false;
        //默认使用Executors提供的FixedThreadPool
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public DefaultServerRemoter(ExecutorService executorService) {
        isShutDown = false;
        this.executorService = executorService;
    }

    @Override
    public void startServer(int port) throws IOException {
        isShutDown = false;
        final ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        log.info("Server started");
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

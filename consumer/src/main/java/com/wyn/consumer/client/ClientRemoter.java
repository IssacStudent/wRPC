package com.wyn.consumer.client;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class ClientRemoter {

    public static final ClientRemoter client = new ClientRemoter();

    public byte[] getDataRemote(byte[] requestData) {

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("127.0.0.1", 9999));//要和服务发布端口对应
            socket.getOutputStream().write(requestData);
            socket.getOutputStream().flush();

            byte[] data = new byte[10240];
            int len = socket.getInputStream().read(data);

            return Arrays.copyOfRange(data, 0, len);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

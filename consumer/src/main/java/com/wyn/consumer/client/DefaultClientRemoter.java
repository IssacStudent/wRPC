package com.wyn.consumer.client;

import com.wyn.common.interfaces.communication.Communication;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * 客户端通信组件
 * @author Yining Wang
 * @date 2023年4月7日10:10:39
 * @since <pre>2023/04/07</pre>
 */
public class DefaultClientRemoter implements Communication {

    private final InetSocketAddress address;

    public DefaultClientRemoter(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public byte[] getResponseData(byte[] requestData) {
        try (Socket socket = new Socket()) {
            socket.connect(address);
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

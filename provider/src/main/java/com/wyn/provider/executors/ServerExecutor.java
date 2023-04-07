package com.wyn.provider.executors;

import com.wyn.common.interfaces.serialize.Serializer;
import com.wyn.common.model.protocol.WProtocolModel;
import com.wyn.common.serialize.KryoSerializer;
import com.wyn.provider.invoker.DefaultInvoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 执行器，负责解析socket中接收到的报文，反序列化后交给Invoker执行
 * @author Yining Wang
 * @date 2023年4月7日13:15:11
 * @since <pre>2023/04/07</pre>
 */
public class ServerExecutor implements Runnable {

    private final Socket socket;

    private static final int MAX_PACKAGE_SIZE = 10240;

    private final Serializer serializer;

    public ServerExecutor(Socket socket) {
        //默认为Kryo序列化
        this(socket, new KryoSerializer());
    }

    public ServerExecutor(Socket socket, Serializer serializer) {
        this.socket = socket;
        this.serializer = serializer;
        if (serializer instanceof KryoSerializer) {
            ((KryoSerializer) serializer).setClazz(WProtocolModel.class);
        }
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            byte[] data = new byte[MAX_PACKAGE_SIZE];
            byte[] resultData = new byte[MAX_PACKAGE_SIZE];
            int len = is.read(data);
            WProtocolModel protocolModel = serializer.deserialize(data, 0, len);
            Object result = DefaultInvoker.PROXY.invoke(protocolModel);
            protocolModel.setResult(result);
            serializer.serialize(protocolModel, resultData);
            os.write(resultData);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert os != null;
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

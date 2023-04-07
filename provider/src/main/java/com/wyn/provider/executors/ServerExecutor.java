package com.wyn.provider.executors;

import com.wyn.common.model.protocol.WProtocolModel;
import com.wyn.common.serialize.KryoSerializer;
import com.wyn.provider.invoker.ServiceInvoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerExecutor implements Runnable {

    private Socket socket;

    private final int MAX_PACKAGE_SIZE = 10240;

    private static final KryoSerializer KRYO_SERIALIZER = new KryoSerializer();

    public ServerExecutor(Socket socket) {
        this.socket = socket;
        KRYO_SERIALIZER.setClazz(WProtocolModel.class);
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
            WProtocolModel protocolModel = KRYO_SERIALIZER.deserialize(data, 0, len);
            Object result = ServiceInvoker.PROXY.process(protocolModel);
            protocolModel.setResult(result);
            KRYO_SERIALIZER.serialize(protocolModel, resultData);
            os.write(resultData);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

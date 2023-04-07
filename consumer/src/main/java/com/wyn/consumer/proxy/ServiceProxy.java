package com.wyn.consumer.proxy;

import com.wyn.common.protocol.WProtocolModel;
import com.wyn.common.serialize.KryoSerializer;
import com.wyn.consumer.client.ClientRemoter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ServiceProxy implements InvocationHandler {

    private static final KryoSerializer KRYO_SERIALIZER = new KryoSerializer();

    private final int MAX_PACKAGE_SIZE = 10240;

    private Class clazz;

    public ServiceProxy(Class clazz) {
        this.clazz = clazz;
        KRYO_SERIALIZER.setClazz(WProtocolModel.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String clazzName = clazz.getName();
        String methodName = method.getName();
        String[] argType = new String[method.getParameterTypes().length];
        for (int i = 0; i < argType.length; i++) {
            argType[i] = method.getParameterTypes()[i].getName();
        }
        WProtocolModel protocolModel = WProtocolModel.builder()
                .clazz(clazzName)
                .method(methodName)
                .argTypes(Arrays.asList(argType))
                .args(Arrays.asList(args))
                .build();
        byte[] inputData = new byte[MAX_PACKAGE_SIZE];
        byte[] resultData = new byte[MAX_PACKAGE_SIZE];
        KRYO_SERIALIZER.serialize(protocolModel, inputData);
        resultData = ClientRemoter.client.getDataRemote(inputData);
        protocolModel = KRYO_SERIALIZER.deserialize(resultData);
        return protocolModel.getResult();
    }
}

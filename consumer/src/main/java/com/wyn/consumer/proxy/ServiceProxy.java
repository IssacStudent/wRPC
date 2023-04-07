package com.wyn.consumer.proxy;

import com.wyn.common.interfaces.communication.Communication;
import com.wyn.common.model.protocol.WProtocolModel;
import com.wyn.common.serialize.KryoSerializer;
import com.wyn.common.interfaces.serialize.Serializer;
import com.wyn.common.utils.YamlReader;
import com.wyn.consumer.client.DefaultClientRemoter;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Arrays;

/**
 * 客户端JDK动态代理类，封装好类、方法、参数为WProtocol协议，并进行序列化，
 * 调用ClientRemoter进行网络通信获取服务端返回结果，再进行反序列化给调用方法。
 * 该部分可以理解为对调用方法的AOP切面。
 *
 * @author Yining Wang
 * @date 2023年4月7日09:55:15
 * @since <pre>2023/04/07</pre>
 */
public class ServiceProxy implements InvocationHandler {

    private final Serializer serializer;

    private final Class<?> clazz;

    public ServiceProxy(Class<?> clazz) {
        //默认为Kryo序列化
        this(new KryoSerializer(), clazz);
    }

    /**
     * 自定义序列化器的构造方法
     * @param serializer 自定义序列化器
     * @param clazz 序列化类
     */
    public ServiceProxy(Serializer serializer, Class<?> clazz) {
        this.serializer = serializer;
        this.clazz = clazz;
        if (serializer instanceof KryoSerializer) {
            ((KryoSerializer) serializer).setClazz(WProtocolModel.class);
        }
    }

    /**
     * 数据包大小
     */
    private static final int MAX_PACKAGE_SIZE = 10240;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
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
        byte[] resultData;
        serializer.serialize(protocolModel, inputData);
        resultData = getClientRemoter().getResponseData(inputData);
        protocolModel = serializer.deserialize(resultData);
        return protocolModel.getResult();
    }

    /**
     * 获得通信组件
     * @return 通信组件
     */
    private Communication getClientRemoter() {
        URL resourcePath = this.getClass().getClassLoader().getResource("");
        assert resourcePath != null;
        File configFile = new File(resourcePath.getPath().concat("wrpc.yaml"));
        if (!configFile.exists()) {
            throw new RuntimeException("No config file found!");
        }
        YamlReader reader = new YamlReader();
        reader.loadProperties(configFile);
        String ip = (String) reader.get("provider.server.ip");
        Integer port = (Integer) reader.get("provider.server.port");
        return new DefaultClientRemoter(new InetSocketAddress(ip, port));
    }
}

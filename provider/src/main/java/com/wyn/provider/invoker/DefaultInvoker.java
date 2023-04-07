package com.wyn.provider.invoker;

import com.wyn.common.interfaces.invoker.Invoker;
import com.wyn.common.model.protocol.WProtocolModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 方法调用器，负责根据WProtocol协议内容来调用方法
 * @author Yining Wang
 * @date 2023年4月7日13:20:18
 * @since <pre>2023/04/07</pre>
 */
public class DefaultInvoker implements Invoker {

    public static final DefaultInvoker PROXY = new DefaultInvoker();

    private static final ConcurrentMap<String, Object> PROCESSOR_INSTANCE_MAP = new ConcurrentHashMap<String, Object>();

    @Override
    public boolean publish(Class<?> clazz, Object obj) {
        return PROCESSOR_INSTANCE_MAP.putIfAbsent(clazz.getName(), obj) != null;
    }

    @Override
    public Object invoke(WProtocolModel protocolModel) {
        try {
            Class<?> aClass = Class.forName(protocolModel.getClazz());
            Class<?>[] types = new Class<?>[protocolModel.getArgTypes().size()];
            for (int i = 0; i < types.length; i++) {
                types[i] = Class.forName(protocolModel.getArgTypes().get(i));
            }
            Method method = aClass.getMethod(protocolModel.getMethod(), types);
            Object obj = PROCESSOR_INSTANCE_MAP.get(protocolModel.getClazz());
            if (obj == null) {
                return null;
            }
            //反射调用方法
            return method.invoke(obj, protocolModel.getArgs().toArray());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

}

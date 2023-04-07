package com.wyn.provider.invoker;

import com.wyn.common.protocol.WProtocolModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServiceInvoker {

    public static final ServiceInvoker PROXY = new ServiceInvoker();

    private static final ConcurrentMap<String, Object> PROCESSOR_INSTANCE_MAP = new ConcurrentHashMap<String, Object>();

    public boolean publish(Class clazz, Object obj) {
        return PROCESSOR_INSTANCE_MAP.putIfAbsent(clazz.getName(), obj) != null;
    }

    public Object process(WProtocolModel protocolModel) {
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
            return method.invoke(obj, protocolModel.getArgs());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

}

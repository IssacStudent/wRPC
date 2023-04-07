package com.wyn.consumer.proxy;

import java.lang.reflect.Proxy;

/**
 * 客户端代理工厂，返回一个被JDK动态代理类（ServiceProxy）增强了的目标类
 * @author Yining Wang
 * @date 2023年4月7日09:51:09
 * @since <pre>2023/04/07</pre>
 */
public class ServiceProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new ServiceProxy(clazz)
        );
    }
}

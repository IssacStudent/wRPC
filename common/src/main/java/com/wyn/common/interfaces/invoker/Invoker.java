package com.wyn.common.interfaces.invoker;

import com.wyn.common.model.protocol.WProtocolModel;

/**
 * 方法调用器接口
 * @author Yining Wang
 * @date 2023年4月7日13:26:02
 * @since <pre>2023/04/07</pre>
 */
public interface Invoker {

    /**
     * 发布一个接口
     * @param clazz 接口类
     * @param obj 接口实现对象
     * @return 发布结果
     */
    boolean publish(Class<?> clazz, Object obj);

    /**
     * 调用方法
     * @param protocolModel 协议
     * @return 方法调用结果
     */
    Object invoke(WProtocolModel protocolModel);

}

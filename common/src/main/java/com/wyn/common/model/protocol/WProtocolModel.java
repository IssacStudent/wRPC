package com.wyn.common.model.protocol;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义RPC协议
 * @author Yining Wang
 * @date 2023年3月30日16:07:43
 * @since <pre>2023/03/30</pre>
 */
@Data
@Builder
public class WProtocolModel implements Serializable {

    /**
     * 类名
     */
    private String clazz;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数类型数组
     */
    private List<String> argTypes;

    /**
     * 参数列表
     */
    private List<Object> args;

    /**
     * 返回值
     */
    private Object result;

}

package com.wyn.common.protocol;

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

    private String clazz;

    private String method;

    private List<String> argTypes;

    private List<Object> args;

}

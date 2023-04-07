package com.wyn.common.interfaces.communication;

/**
 * 通信客户端接口
 * @author Yining Wang
 * @date 2023年4月7日10:31:00
 * @since <pre>2023/04/07</pre>
 */
public interface Client {

    /**
     * 获取数据
     * @param requestData 请求数据
     * @return 响应数据
     */
    byte[] getResponseData(byte[] requestData);

}

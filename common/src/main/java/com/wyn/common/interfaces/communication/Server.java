package com.wyn.common.interfaces.communication;

import java.io.IOException;

/**
 * 通信服务端接口
 * @author Yining Wang
 * @date 2023年4月7日13:22:09
 * @since <pre>2023/04/07</pre>
 */
public interface Server {

    /**
     * 开启一个服务端
     * @param port 端口号
     * @throws IOException
     */
    void startServer(int port) throws IOException;

}

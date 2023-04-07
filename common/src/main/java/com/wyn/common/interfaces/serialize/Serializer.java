package com.wyn.common.interfaces.serialize;

/**
 * 序列化工具
 * @author Yining Wang
 * @date 2023年3月7日21:59:44
 * @since <pre>2023/03/07</pre>
 */
public interface Serializer {
    /**
     * 序列化
     * @param obj 序列化对象
     * @param bytes 字节数组
     */
    void serialize(Object obj, byte[] bytes);

    /**
     * 序列化
     * @param obj 序列化对象
     * @param bytes 字节数组
     * @param offset 数组偏移
     * @param count 数组大小
     */
    void serialize(Object obj, byte[] bytes, int offset, int count);

    /**
     * 反序列化
     * @param bytes 字节数组
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes);


    /**
     * 反序列化
     * @param bytes 字节数组
     * @param offset 数组偏移
     * @param count 数组大小
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes, int offset, int count);
}

package com.wyn.common.serialize;

/**
 * 序列化工具
 * @author Yining Wang
 * @date 2023年3月7日21:59:44
 * @since <pre>2023/03/07</pre>
 */
public interface Serializer {
    /**
     * 序列化
     * @param t
     * @param bytes
     */
    public void serialize(Object t,byte[] bytes);

    /**
     * 序列化
     * @param obj
     * @param bytes
     * @param offset
     * @param count
     */
    public void serialize(Object obj, byte[] bytes, int offset, int count);

    /**
     * 反序列化
     * @param bytes -字节数组
     * @return T<T>
     */
    public <T>T deserialize(byte[] bytes);


    /**
     * 反序列化
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    public <T>T deserialize(byte[] bytes, int offset, int count);
}

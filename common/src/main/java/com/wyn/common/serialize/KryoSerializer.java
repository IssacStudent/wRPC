package com.wyn.common.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.wyn.common.interfaces.serialize.Serializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * Kryo的序列化工具
 *
 * @author Yining Wang
 * @date 2023年3月30日16:43:11
 * @since <pre>2023/03/30</pre>
 */
public class KryoSerializer implements Serializer {

    private Class<?> clazz = null;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * 保证线程安全，使用ThreadLocal
     */
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(clazz, new BeanSerializer<>(kryo, clazz));
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        return kryo;
    });

    private final ThreadLocal<Output> outputThreadLocal = new ThreadLocal<>();

    private final ThreadLocal<Input> inputThreadLocal = new ThreadLocal<>();

    @Override
    public void serialize(Object obj, byte[] bytes) {
        Kryo kryo = kryoThreadLocal.get();
        Output output = getOutput(bytes);
        kryo.writeObjectOrNull(output, obj, obj.getClass());
        output.flush();
    }

    @Override
    public void serialize(Object o, byte[] bytes, int offset, int count) {
        Kryo kryo = kryoThreadLocal.get();
        Output output = getOutput(bytes, offset, count);
        kryo.writeObjectOrNull(output, o, o.getClass());
        output.flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes) {
        Kryo kryo = kryoThreadLocal.get();
        Input input = getInput(bytes);
        return (T) kryo.readObjectOrNull(input, clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes, int offset, int count) {
        Kryo kryo = kryoThreadLocal.get();
        Input input = getInput(bytes, offset, count);
        return (T) kryo.readObjectOrNull(input, clazz);
    }

    private Output getOutput(byte[] bytes) {
        Output output = outputThreadLocal.get();
        if (output == null) {
            output = new Output();
            outputThreadLocal.set(output);
        }
        if (bytes != null) {
            output.setBuffer(bytes);
        }
        return output;
    }

    private Output getOutput(byte[] bytes, int offset, int count) {
        Output output = outputThreadLocal.get();
        if (output == null) {
            output = new Output();
            outputThreadLocal.set(output);
        }
        if (bytes != null) {
            output.writeBytes(bytes, offset, count);
        }
        return output;
    }

    private Input getInput(byte[] bytes) {
        Input input = inputThreadLocal.get();
        if (input == null) {
            input = new Input();
            inputThreadLocal.set(input);
        }
        if (bytes != null) {
            input.setBuffer(bytes);
        }
        return input;
    }

    private Input getInput(byte[] bytes, int offset, int count) {
        Input input = inputThreadLocal.get();
        if (input == null) {
            input = new Input();
            inputThreadLocal.set(input);
        }
        if (bytes != null) {
            input.setBuffer(bytes, offset, count);
        }
        return input;
    }
}

package com.wyn.serialize;

public class KryoSerializer implements Serializer {

    @Override
    public void serialize(Object t, byte[] bytes) {

    }

    @Override
    public void serialize(Object obj, byte[] bytes, int offset, int count) {

    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes, int offset, int count) {
        return null;
    }
}

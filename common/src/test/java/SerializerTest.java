import com.wyn.common.model.protocol.WProtocolModel;
import com.wyn.common.serialize.KryoSerializer;
import org.junit.Test;

import java.util.Arrays;

public class SerializerTest {
    @Test
    public void test() {
        byte[] bytes =new byte[200];
        String[] strings = {"s","s1"};
        System.out.println(Arrays.toString(bytes));
        KryoSerializer kryoSerializer = new KryoSerializer();
        kryoSerializer.setClazz(WProtocolModel.class);
        WProtocolModel protocol = WProtocolModel.builder()
                .clazz("clazztest")
                .method("methodzz")
                .argTypes(Arrays.asList(new String[]{"asd", "asd"}))
                .args(Arrays.asList(new Object[]{1, 2.2})).build();
        kryoSerializer.serialize(protocol,bytes);
        System.out.println(protocol.toString());
        System.out.println(Arrays.toString(bytes));
        System.out.println("=====================================");
        WProtocolModel testSerialization1 = kryoSerializer.deserialize(bytes);
        System.out.println(testSerialization1.toString());
        System.out.println(Arrays.toString(bytes));
        System.out.println();
    }
}

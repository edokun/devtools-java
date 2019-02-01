package net.edokun.tools;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PojoFactoryTest {

    @Test
    @DisplayName("test build method using using Pojo class, should return an instance.")
    void testPojoFactoryInstantiatePojoSuccess() {
        PojoFactory<TestPojo> factory = PojoFactory.getInstance(TestPojo.class);
        TestPojo test = factory.build();
        assertNotNull(test);
    }

    @Test
    @DisplayName("test build method using using Pojo2 class, should fail because do not have default zero-arg constructor.")
    void testPojoFactoryInstantiatePojoWithoutDefaultConstructorFail() {
        PojoFactory<TestPojo2> factory = PojoFactory.getInstance(TestPojo2.class);
        assertThrows(RuntimeException.class, () -> {
            factory.build();
        });
    }

    @Test
    @DisplayName("test build method using an instantiated class, should just initialize attributes.")
    void testPojoFactoryFillAttributesOfInstantiatedPojoSuccess() {
        PojoFactory<TestPojo> factory = PojoFactory.getInstance(TestPojo.class);
        TestPojo test = new TestPojo();
        factory.setInstatiatedPojo(test);
        test = factory.build();
        verifyAttributesNotNull(test);
    }

    @Test
    @DisplayName("test build method using Pojo class with randomized values, should return an instance with random values.")
    void testPojoFactoryFillAttributesWithRandomValuesSuccess() {
        PojoFactory<TestPojo> factory = PojoFactory.getInstance(TestPojo.class);
        factory.randomizeValues();
        TestPojo test = factory.build();
        verifyAttributesNotNull(test);
    }

    @Test
    @DisplayName("test build method using Pojo class, should return an instance with default values for the attributes.")
    void testPojoFactoryInstantiatePrimitiveValuesSuccess() {
        PojoFactory<TestPojo> factory = PojoFactory.getInstance(TestPojo.class);
        TestPojo test = factory.build();
        verifyDefaultValues(test);
    }

    private void verifyDefaultValues(TestPojo pojo) {
        //By default Java assign these values to primitive variables
        assertEquals(pojo.getIntValue(),0);
        assertEquals(pojo.getShortValue(),0);
        assertEquals(pojo.getLongValue(),0L);
        assertEquals(pojo.getCharValue(),'\u0000');
        assertEquals(pojo.getFloatValue(),0.0F);
        assertEquals(pojo.getDoubleValue(),0.0);
        assertEquals(pojo.isBoolValue(),false);
        assertEquals(pojo.getByteValue(),Byte.valueOf("0").byteValue());

        //Verify default values for Wrappers of primitives
        assertEquals(pojo.getIntegerVar(),Integer.valueOf(0));
        assertEquals(pojo.getShortVar(),Short.valueOf("0"));
        assertEquals(pojo.getLongVar(),Long.valueOf(0L));
        assertEquals(pojo.getCharVar(),Character.valueOf('\u0000'));
        assertEquals(pojo.getFloatVar(),Float.valueOf(0.0F));
        assertEquals(pojo.getDoubleVar(),Double.valueOf(0.0));
        assertEquals(pojo.getBoolVar(),false);
        assertEquals(pojo.getByteVar(),Byte.valueOf("0"));

        //Verify default value for String
        assertEquals(pojo.getStringVar(),"");

    }

    private void verifyAttributesNotNull(TestPojo pojo) {
        //Verify default values for String, Object and Wrappers of primitives
        assertNotNull(pojo.getStringVar());
        assertNotNull(pojo.getObjectVar());
        assertNotNull(pojo.getIntegerVar());
        assertNotNull(pojo.getShortVar());
        assertNotNull(pojo.getLongVar());
        assertNotNull(pojo.getCharVar());
        assertNotNull(pojo.getFloatVar());
        assertNotNull(pojo.getDoubleVar());
        assertNotNull(pojo.getBoolVar());
        assertNotNull(pojo.getByteVar());

    }
}

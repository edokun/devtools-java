package net.edokun.tools;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PojoFactoryTest {

    @Test
    @DisplayName("test build method using simple Pojo, should return an instance.")
    void testPojoFactoryInstantiatePojoSuccess() {
        PojoFactory<TestPojo> factory = PojoFactory.getInstance(TestPojo.class);
        TestPojo test = null;
        test = factory.build();
        assertNotNull(test);
        assertNotNull(test.getStringVar());
        assertEquals(test.getStringVar(),"stringVar");
    }

    @Test
    @DisplayName("test build method using an instantiated simple Pojo, should just initialize attributes.")
    void testPojoFactoryFillAttributesOfInstantiatedPojoSuccess() {
        PojoFactory<TestPojo> factory = PojoFactory.getInstance(TestPojo.class);
        TestPojo test = new TestPojo();
        factory.setInstatiatedPojo(test);
        test = factory.build();
        assertNotNull(test.getStringVar());
        assertEquals(test.getStringVar(),"stringVar");
    }
}

package deors.demos.annotations.beaninfo.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.beans.PropertyDescriptor;

import org.junit.Test;

public class ArticleBeanInfoTest {

    private static final int EXPECTED_GENERATED_METHOD_COUNT = 3;
    private static final String GENERATED_BEAN_INFO_CLASS_NAME =
        "deors.demos.annotations.beaninfo.client.ArticleBeanInfo";

    @Test
    public void testGeneratedBeanInfoIsAvailableAndExecutable() throws ReflectiveOperationException {

        Class<?> beanInfoClass = Class.forName(GENERATED_BEAN_INFO_CLASS_NAME);
        Object beanInfo = beanInfoClass.getDeclaredConstructor().newInstance();

        PropertyDescriptor idDescriptor = invokeDescriptorMethod(
            beanInfoClass, beanInfo, "idPropertyDescriptor");
        PropertyDescriptor departmentDescriptor = invokeDescriptorMethod(
            beanInfoClass, beanInfo, "departmentPropertyDescriptor");
        PropertyDescriptor statusDescriptor = invokeDescriptorMethod(
            beanInfoClass, beanInfo, "statusPropertyDescriptor");

        assertNull(idDescriptor);
        assertNull(departmentDescriptor);
        assertNull(statusDescriptor);
        assertEquals(EXPECTED_GENERATED_METHOD_COUNT, beanInfoClass.getDeclaredMethods().length);
    }

    private PropertyDescriptor invokeDescriptorMethod(Class<?> beanInfoClass, Object beanInfo,
        String methodName) throws ReflectiveOperationException {

        Object value = beanInfoClass.getMethod(methodName).invoke(beanInfo);
        return PropertyDescriptor.class.cast(value);
    }
}

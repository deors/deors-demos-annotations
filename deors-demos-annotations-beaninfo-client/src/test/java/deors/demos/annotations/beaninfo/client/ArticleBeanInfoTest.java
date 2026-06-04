package deors.demos.annotations.beaninfo.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import org.junit.Test;

public class ArticleBeanInfoTest {

    private static final int EXPECTED_GENERATED_METHOD_COUNT = 3;
    private static final String GENERATED_BEAN_INFO_CLASS_NAME =
        "deors.demos.annotations.beaninfo.client.ArticleBeanInfo";

    @Test
    public void testGeneratedBeanInfoIsAvailableAndExecutable() throws ReflectiveOperationException {

        Class<?> beanInfoClass = Class.forName(GENERATED_BEAN_INFO_CLASS_NAME);
        Object beanInfo = beanInfoClass.getDeclaredConstructor().newInstance();

        assertTrue(SimpleBeanInfo.class.isAssignableFrom(beanInfoClass));
        assertEquals(PropertyDescriptor.class,
            beanInfoClass.getMethod("idPropertyDescriptor").getReturnType());
        assertEquals(PropertyDescriptor.class,
            beanInfoClass.getMethod("departmentPropertyDescriptor").getReturnType());
        assertEquals(PropertyDescriptor.class,
            beanInfoClass.getMethod("statusPropertyDescriptor").getReturnType());

        invokeDescriptorMethod(
            beanInfoClass, beanInfo, "idPropertyDescriptor");
        invokeDescriptorMethod(
            beanInfoClass, beanInfo, "departmentPropertyDescriptor");
        invokeDescriptorMethod(
            beanInfoClass, beanInfo, "statusPropertyDescriptor");

        assertEquals(EXPECTED_GENERATED_METHOD_COUNT, beanInfoClass.getDeclaredMethods().length);
    }

    private void invokeDescriptorMethod(Class<?> beanInfoClass, Object beanInfo,
        String methodName) throws ReflectiveOperationException {

        beanInfoClass.getMethod(methodName).invoke(beanInfo);
    }
}

package deors.demos.annotations.velocity.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.beans.BeanDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class ArticleBeanInfoTest {

    private static final int EXPECTED_PROPERTY_COUNT = 3;
    private static final int EXPECTED_METHOD_COUNT = 6;
    private static final String GENERATED_BEAN_INFO_CLASS_NAME =
        "deors.demos.annotations.velocity.client.ArticleBeanInfo";
    private static final String GENERATED_BEAN_INFO_SOURCE_PATH =
        "target/generated-sources/annotations/" + GENERATED_BEAN_INFO_CLASS_NAME.replace('.', '/')
            + ".java";
    private static final String GENERATED_BEAN_INFO_CLASS_PATH =
        "target/classes/" + GENERATED_BEAN_INFO_CLASS_NAME.replace('.', '/') + ".class";

    @Test
    public void testGeneratedBeanInfoIsValidAndExecutable() throws ReflectiveOperationException {

        assertTrue(Files.exists(Paths.get(GENERATED_BEAN_INFO_SOURCE_PATH)));
        assertTrue(Files.exists(Paths.get(GENERATED_BEAN_INFO_CLASS_PATH)));

        Class<?> beanInfoClass = Class.forName(GENERATED_BEAN_INFO_CLASS_NAME);
        Object beanInfo = beanInfoClass.getDeclaredConstructor().newInstance();

        BeanDescriptor beanDescriptor = invokeBeanInfoMethod(
            beanInfoClass, beanInfo, "getBeanDescriptor", BeanDescriptor.class);
        assertNotNull(beanDescriptor);
        assertEquals("Article", beanDescriptor.getName());

        PropertyDescriptor[] properties = invokeBeanInfoMethod(
            beanInfoClass, beanInfo, "getPropertyDescriptors", PropertyDescriptor[].class);
        assertNotNull(properties);
        assertEquals(EXPECTED_PROPERTY_COUNT, properties.length);

        MethodDescriptor[] methods = invokeBeanInfoMethod(
            beanInfoClass, beanInfo, "getMethodDescriptors", MethodDescriptor[].class);
        assertNotNull(methods);
        assertEquals(EXPECTED_METHOD_COUNT, methods.length);

        MethodDescriptor invalidate = invokeBeanInfoMethod(
            beanInfoClass, beanInfo, "getInvalidateMethodDescriptor", MethodDescriptor.class);
        assertNotNull(invalidate);

        Article article = new Article();
        invalidate.getMethod().invoke(article, "sample-cause");
        assertTrue(article.isInvalid());
    }

    private <T> T invokeBeanInfoMethod(Class<?> beanInfoClass, Object beanInfo, String methodName,
        Class<T> type) throws ReflectiveOperationException {

        Object value = beanInfoClass.getMethod(methodName).invoke(beanInfo);
        return type.cast(value);
    }
}

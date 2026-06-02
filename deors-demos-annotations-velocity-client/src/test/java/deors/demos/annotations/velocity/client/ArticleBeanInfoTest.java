package deors.demos.annotations.velocity.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.beans.BeanDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

import org.junit.Test;

public class ArticleBeanInfoTest {

    private static final int EXPECTED_PROPERTY_COUNT = 3;
    private static final int EXPECTED_METHOD_COUNT = 6;

    @Test
    public void testGeneratedBeanInfoIsValidAndExecutable() throws ReflectiveOperationException {

        ArticleBeanInfo beanInfo = new ArticleBeanInfo();

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        assertNotNull(beanDescriptor);
        assertEquals("Article", beanDescriptor.getName());

        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        assertNotNull(properties);
        assertEquals(EXPECTED_PROPERTY_COUNT, properties.length);

        MethodDescriptor[] methods = beanInfo.getMethodDescriptors();
        assertNotNull(methods);
        assertEquals(EXPECTED_METHOD_COUNT, methods.length);

        MethodDescriptor invalidate = beanInfo.getInvalidateMethodDescriptor();
        assertNotNull(invalidate);

        Article article = new Article();
        invalidate.getMethod().invoke(article, "sample-cause");
        assertTrue(article.isInvalid());
    }
}

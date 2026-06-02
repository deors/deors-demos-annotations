package deors.demos.annotations.velocity.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.beans.BeanDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

import org.junit.Test;

public class ArticleBeanInfoTest {

    @Test
    public void generatedBeanInfoShouldBeValidAndExecutable() throws Exception {

        ArticleBeanInfo beanInfo = new ArticleBeanInfo();

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        assertNotNull(beanDescriptor);
        assertEquals("Article", beanDescriptor.getName());

        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        assertNotNull(properties);
        assertEquals(3, properties.length);

        MethodDescriptor[] methods = beanInfo.getMethodDescriptors();
        assertNotNull(methods);
        assertEquals(6, methods.length);

        MethodDescriptor invalidate = beanInfo.getInvalidateMethodDescriptor();
        assertNotNull(invalidate);

        Article article = new Article();
        invalidate.getMethod().invoke(article, "sample-cause");
        assertTrue(article.isInvalid());
    }
}

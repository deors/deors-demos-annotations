package deors.demos.annotations.base.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.junit.Test;

import deors.demos.annotations.base.Complexity;
import deors.demos.annotations.base.ComplexityLevel;

public class SimpleAnnotationsTestTest {

    @Test
    public void testComplexityAnnotationsRemainExecutable() throws NoSuchMethodException {

        Complexity classComplexity = SimpleAnnotationsTest.class.getAnnotation(Complexity.class);
        assertNotNull(classComplexity);
        assertEquals(ComplexityLevel.VERY_SIMPLE, classComplexity.value());

        Method method = SimpleAnnotationsTest.class.getMethod("theMethod");
        Complexity methodComplexity = method.getAnnotation(Complexity.class);
        assertNotNull(methodComplexity);
        assertEquals(ComplexityLevel.MEDIUM, methodComplexity.value());

        new SimpleAnnotationsTest().theMethod();
    }
}

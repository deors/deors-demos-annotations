package deors.demos.annotations.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type modeling the complexity level of types and methods.
 *
 * @author deors
 * @version 1.0
 */
@Documented
@Target({
    ElementType.TYPE,
    ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Complexity {

    /** The complexity level. */
    ComplexityLevel value() default ComplexityLevel.MEDIUM;
}

package deors.demos.annotations.beaninfo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The BeanInfo annotation type, used to capture descriptive information of the bean
 * to be latter used to generate BeanInfo types suitable for visual editors.
 *
 * @author deors
 * @version 1.0
 */
@Documented
@Target({
    ElementType.TYPE,
    ElementType.METHOD,
    ElementType.CONSTRUCTOR,
    ElementType.FIELD,
    ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
public @interface BeanInfo {

    /** The element description. */
    String description() default "";

    /** Whether the element is an expert feature. */
    boolean expert() default false;

    /** Whether the element should be hidden in menus of visual editors. */
    boolean hidden() default false;

    /** Whether the element should be prioritized in menus of visual editors. */
    boolean preferred() default false;
}

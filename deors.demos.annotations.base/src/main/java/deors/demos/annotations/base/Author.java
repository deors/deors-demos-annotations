package deors.demos.annotations.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type modeling authorship information for types.
 *
 * @author deors
 * @version 1.0
 */
@Documented
@Target({
    ElementType.TYPE,
    ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
public @interface Author {

    /** Author's name. */
    String name();

    /** Date of creation. */
    String created();

    /** Revision number. */
    int revision() default 1;

    /** Array of reviewer names. */
    String[] reviewers() default {};
}

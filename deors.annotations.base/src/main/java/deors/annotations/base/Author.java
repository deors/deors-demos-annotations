package deors.annotations.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({
    ElementType.TYPE,
    ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
public @interface Author {

    String name();
    String created();
    int revision() default 1;
    String[] reviewers() default {};
}

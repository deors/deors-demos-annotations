package deors.demos.annotations.base.client;

import deors.demos.annotations.base.Author;
import deors.demos.annotations.base.Complexity;
import deors.demos.annotations.base.ComplexityLevel;
import deors.demos.annotations.base.WorkProduct;

/**
 * A simple class using Author, Complexity and WorkProduct annotations.
 *
 * @author deors
 * @version 1.0
 */
@Author(name = "Albert",
        created = "17/09/2010",
        revision = 3,
        reviewers = {"George", "Fred"}) // multiple attributes are separated by commas
@WorkProduct("WP00000182") // if only one annotation attribute is defined
                           // and its name is 'value', it can be omitted
@Complexity(ComplexityLevel.VERY_SIMPLE)
public class SimpleAnnotationsTest {

    /**
     * Default constructor.
     */
    @Author(name = "Albert",
            created = "17/09/2010") // this annotation types applies also to constructors
                                    // default values assumed for revision and reviewers
    public SimpleAnnotationsTest() {

        super();
    }

    /**
     * Some method.
     */
    @Complexity() // this annotation type applies also to methods
                  // the default value 'ComplexityLevel.MEDIUM' is assumed
    public void theMethod() {

        System.out.println("console output");
    }
}

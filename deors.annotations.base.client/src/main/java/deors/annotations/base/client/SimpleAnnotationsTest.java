package deors.annotations.base.client;

import deors.annotations.base.Author;
import deors.annotations.base.Complexity;
import deors.annotations.base.ComplexityLevel;
import deors.annotations.base.WorkProduct;

@Author(name = "Albert",
        created = "17/09/2010",
        revision = 3,
        reviewers = {"George", "Fred"}) // multiple attributes are separated by commas
@WorkProduct("WP00000182") // if only one annotation attribute is defined
                           // and its name is 'value', it can be omitted
@Complexity(ComplexityLevel.VERY_SIMPLE)
public class SimpleAnnotationsTest {

    @Author(name = "Albert",
            created = "17/09/2010") // this annotation types applies also to constructors
                                    // default values assumed for revision and reviewers
    public SimpleAnnotationsTest() {

        super();
    }

    @Complexity() // this annotation type applies also to methods
                  // the default value 'ComplexityLevel.MEDIUM' is assumed
    public void theMethod() {

        System.out.println("console output");
    }
}

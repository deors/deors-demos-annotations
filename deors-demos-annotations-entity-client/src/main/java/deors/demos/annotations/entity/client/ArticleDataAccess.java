package deors.demos.annotations.entity.client;

import java.util.List;

import deors.demos.annotations.entity.BaseDataAccess;
import deors.demos.annotations.entity.GenerateDataAccess;

/**
 * A data access interface for the Article bean.
 *
 * @author deors
 * @version 1.0
 */
@GenerateDataAccess
public interface ArticleDataAccess
    extends BaseDataAccess<Article, String> {

    /**
     * A custom query that uses two parameters as search criteria.
     *
     * @param criteria1 the first search criteria
     * @param criteria2 the second search criteria
     * @return the list of beans corresponding to the given criteria
     */
    @GenerateDataAccess("select custom query clauses")
    List<Article> getByCustomCriteria(String criteria1, String criteria2);
}

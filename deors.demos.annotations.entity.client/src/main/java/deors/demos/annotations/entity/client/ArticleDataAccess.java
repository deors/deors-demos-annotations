package deors.demos.annotations.entity.client;

import java.util.List;

import deors.demos.annotations.entity.BaseDataAccess;
import deors.demos.annotations.entity.GenerateDataAccess;

@GenerateDataAccess
public interface ArticleDataAccess
    extends BaseDataAccess<Article> {

    @GenerateDataAccess("select custom query clauses")
    List<Article> getByCustomCriteria(String criteria1, String criteria2);
}

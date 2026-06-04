package deors.demos.annotations.entity.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.junit.Test;

public class GeneratedEntityArtifactsTest {

    private static final String GENERATED_ENTITY_CLASS_NAME =
        "deors.demos.annotations.entity.client.ArticleImpl";
    private static final String GENERATED_DATA_ACCESS_CLASS_NAME =
        "deors.demos.annotations.entity.client.ArticleDataAccessImpl";

    @Test
    public void testGeneratedEntityClassImplementsExpectedContract()
        throws ReflectiveOperationException {

        Class<?> entityClass = Class.forName(GENERATED_ENTITY_CLASS_NAME);
        assertTrue(Article.class.isAssignableFrom(entityClass));

        Entity entity = entityClass.getAnnotation(Entity.class);
        assertNotNull(entity);
        Table table = entityClass.getAnnotation(Table.class);
        assertNotNull(table);
        assertEquals("ARTICLE", table.name());

        Article article = Article.class.cast(entityClass.getDeclaredConstructor().newInstance());
        article.setId("A-1");
        article.setDepartment(10);
        article.setStatus("active");

        assertEquals("A-1", article.getId());
        assertEquals(10, article.getDepartment());
        assertEquals("active", article.getStatus());

        Method getId = entityClass.getMethod("getId");
        assertNotNull(getId.getAnnotation(Id.class));

        Column idColumn = getId.getAnnotation(Column.class);
        assertNotNull(idColumn);
        assertEquals("ID", idColumn.name());
    }

    @Test
    public void testGeneratedDataAccessClassImplementsExpectedContract()
        throws ReflectiveOperationException {

        Class<?> dataAccessClass = Class.forName(GENERATED_DATA_ACCESS_CLASS_NAME);
        assertTrue(ArticleDataAccess.class.isAssignableFrom(dataAccessClass));

        Method customCriteriaMethod = dataAccessClass.getMethod(
            "getByCustomCriteria", String.class, String.class);
        assertEquals(List.class, customCriteriaMethod.getReturnType());
    }
}

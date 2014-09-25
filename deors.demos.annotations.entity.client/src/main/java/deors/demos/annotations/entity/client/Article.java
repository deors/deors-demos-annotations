package deors.demos.annotations.entity.client;

import deors.demos.annotations.entity.GenerateEntity;

/**
 * This JavaBean represents an Article in the On-line Store application.
 *
 * @author deors
 * @version 1.0
 */
@GenerateEntity
public interface Article {

    @GenerateEntity(id = true)
    public String getId();
    public void setId(String id);

    public int getDepartment();
    public void setDepartment(int department);

    public String getStatus();
    public void setStatus(String status);
}

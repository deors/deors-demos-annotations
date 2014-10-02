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

    /**
     * Getter for id property.
     *
     * @return the property value
     */
    @GenerateEntity(id = true)
    String getId();

    /**
     * Setter for id property.
     *
     * @param id the new property value
     */
    void setId(String id);

    /**
     * Getter for department property.
     *
     * @return the property value
     */
    int getDepartment();

    /**
     * Setter for department property.
     *
     * @param department the new property value
     */
    void setDepartment(int department);

    /**
     * Getter for status property.
     *
     * @return the property value
     */
    String getStatus();

    /**
     * Setter for status property.
     *
     * @param status the new property value
     */
    void setStatus(String status);
}

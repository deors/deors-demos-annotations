package deors.demos.annotations.beaninfo.client;

import deors.demos.annotations.beaninfo.BeanInfo;

/**
 * Article class that uses the BeanInfo annotation.
 *
 * @author deors
 * @version 1.0
 */
@BeanInfo
public class Article {

    /** Article's id. */
    @BeanInfo
    private String id;

    /** Article's department id. */
    @BeanInfo
    private int department;

    /** Article's status string. */
    @BeanInfo
    private String status;

    /**
     * Default constructor.
     */
    public Article() {
        super();
    }

    /**
     * Getter for id property.
     *
     * @return the property value
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id property.
     *
     * @param id the new property value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for department property.
     *
     * @return the property value
     */
    public int getDepartment() {
        return department;
    }

    /**
     * Setter for department property.
     *
     * @param department the new property value
     */
    public void setDepartment(int department) {
        this.department = department;
    }

    /**
     * Getter for status property.
     *
     * @return the property value
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for status property.
     *
     * @param status the new property value
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Action to activate the article.
     */
    @BeanInfo
    public void activate() {
        setStatus("active");
    }

    /**
     * Action to deactivate the article.
     */
    @BeanInfo
    public void deactivate() {
        setStatus("inactive");
    }
}

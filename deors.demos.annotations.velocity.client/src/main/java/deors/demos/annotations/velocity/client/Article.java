package deors.demos.annotations.velocity.client;

import deors.demos.annotations.velocity.BeanInfo;

/**
 * This JavaBean represents an Article in the On-line Store application.
 *
 * @author deors
 * @version 1.0
 */
@BeanInfo(description = "This JavaBean represents an Article in the On-line Store application")
public class Article {

    /** Active status string. */
    private static final String ACTIVE = "active";

    /** Inactive status string. */
    private static final String INACTIVE = "inactive";

    /** Invalid status string. */
    private static final String INVALID = "invalid";

    /** The unique identifier for this Article in the catalog. */
    @BeanInfo(description = "The unique identifier for this Article in the catalog")
    private String id;

    /** The department number where this Article belongs to. */
    @BeanInfo(description = "The department number where this Article belongs to")
    private int department;

    /** Textual representation of this Article status. */
    @BeanInfo(description = "Textual representation of this Article status")
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
        setStatus(ACTIVE);
    }

    /**
     * Action to deactivate the article.
     */
    @BeanInfo
    public void deactivate() {
        setStatus(INACTIVE);
    }

    /**
     * Action to invalidate the article.
     *
     * @param cause the cause why the article is invalidated
     */
    @BeanInfo(description = "Action to invalidate the Article, given the cause")
    public void invalidate(
        @BeanInfo(description = "The cause why the Article is invalidated") String cause) {
        setStatus(INVALID + ": " + cause);
    }

    /**
     * Whether the article is active.
     *
     * @return whether the article is active
     */
    @BeanInfo
    public boolean isActive() {
        return ACTIVE.equals(status);
    }

    /**
     * Whether the article is inactive.
     *
     * @return whether the article is inactive
     */
    @BeanInfo
    public boolean isInactive() {
        return INACTIVE.equals(status);
    }

    /**
     * Whether the article is invalid.
     *
     * @return whether the article is invalid
     */
    @BeanInfo
    public boolean isInvalid() {
        return status != null && status.startsWith(INVALID);
    }
}

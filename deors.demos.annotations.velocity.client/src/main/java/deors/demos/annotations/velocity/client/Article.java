package deors.demos.annotations.velocity.client;

import deors.demos.annotations.beaninfo.BeanInfo;

@BeanInfo(description = "This JavaBean represents an Article in the On-line Store application")
public class Article {

    @BeanInfo(description = "The unique identifier for this Article in the catalogue")
    private String id;

    @BeanInfo(description = "The department number where this Article belongs to")
    private int department;

    @BeanInfo(description = "Textual representation of this Article status")
    private String status;

    public Article() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @BeanInfo
    public void activate() {
        setStatus("active");
    }

    @BeanInfo
    public void deactivate() {
        setStatus("inactive");
    }

    @BeanInfo(description = "Action to invalidate the Article, given the cause")
    public void invalidate(
        @BeanInfo(description = "The cause why the Article is invalidated") String cause) {
        setStatus("invalid: " + cause);
    }

    @BeanInfo
    public boolean isActive() {
        return "active".equals(status);
    }

    @BeanInfo
    public boolean isInactive() {
        return "inactive".equals(status);
    }

    @BeanInfo
    public boolean isInvalid() {
        return status != null && status.startsWith("invalid");
    }
}

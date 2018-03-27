package deors.demos.annotations.velocity.processors;

/**
 * Model for a type BeanInfo.
 *
 * @author deors
 * @version 1.0
 */
public class BeanInfoTypeModel {

    /** The package name. */
    private String packageName;

    /** The class simple name. */
    private String className;

    /** The class fully qualified name. */
    private String qualifiedName;

    /** The BeanInfo class simple name. */
    private String beanInfoClassName;

    /** The BeanInfo class fully qualified name. */
    private String beanInfoQualifiedName;

    /** Type description. */
    private String description;

    /** Whether the type represents an expert feature. */
    private boolean expert;

    /** Whether the type should be hidden in menus of visual editors. */
    private boolean hidden;

    /** Whether the type should be prioritized in menus of visual editors. */
    private boolean preferred;

    /**
     * Default constructor.
     */
    public BeanInfoTypeModel() {
        super();
    }

    /**
     * Getter for packageName property.
     *
     * @return the property value.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Getter for className property.
     *
     * @return the property value.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Getter for qualifiedName property.
     *
     * @return the property value.
     */
    public String getQualifiedName() {
        return qualifiedName;
    }

    /**
     * Getter for beanInfoClassName property.
     *
     * @return the property value.
     */
    public String getBeanInfoClassName() {
        return beanInfoClassName;
    }

    /**
     * Getter for beanInfoQualifiedName property.
     *
     * @return the property value.
     */
    public String getBeanInfoQualifiedName() {
        return beanInfoQualifiedName;
    }

    /**
     * Getter for description property.
     *
     * @return the property value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for expert property.
     *
     * @return the property value.
     */
    public boolean isExpert() {
        return expert;
    }

    /**
     * Getter for hidden property.
     *
     * @return the property value.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Getter for preferred property.
     *
     * @return the property value.
     */
    public boolean isPreferred() {
        return preferred;
    }

    /**
     * Setter for packageName property.
     *
     * @param packageName the packageName to set
     */
    void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Setter for className property.
     *
     * @param className the className to set
     */
    void setClassName(String className) {
        this.className = className;
    }

    /**
     * Setter for qualifiedName property.
     *
     * @param qualifiedName the qualifiedName to set
     */
    void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    /**
     * Setter for beanInfoClassName property.
     *
     * @param beanInfoClassName the beanInfoClassName to set
     */
    void setBeanInfoClassName(String beanInfoClassName) {
        this.beanInfoClassName = beanInfoClassName;
    }

    /**
     * Setter for beanInfoQualifiedName property.
     *
     * @param beanInfoQualifiedName the beanInfoQualifiedName to set
     */
    void setBeanInfoQualifiedName(String beanInfoQualifiedName) {
        this.beanInfoQualifiedName = beanInfoQualifiedName;
    }

    /**
     * Setter for description property.
     *
     * @param description the description to set
     */
    void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for expert property.
     *
     * @param expert the expert to set
     */
    void setExpert(boolean expert) {
        this.expert = expert;
    }

    /**
     * Setter for hidden property.
     *
     * @param hidden the hidden to set
     */
    void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Setter for preferred property.
     *
     * @param preferred the preferred to set
     */
    void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
}

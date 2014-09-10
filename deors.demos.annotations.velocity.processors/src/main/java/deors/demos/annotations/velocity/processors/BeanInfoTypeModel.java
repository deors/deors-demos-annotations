package deors.demos.annotations.velocity.processors;

/**
 * Model for a type BeanInfo.
 *
 * @author deors
 * @version 1.0
 */
public class BeanInfoTypeModel {

    /** The package name. */
    String packageName;

    /** The class simple name. */
    String className;

    /** The class fully qualified name. */
    String qualifiedName;

    /** The BeanInfo class simple name. */
    String beanInfoClassName;

    /** The BeanInfo class fully qualified name. */
    String beanInfoQualifiedName;

    /** Type description. */
    String description;

    /** Whether the type represents an expert feature. */
    boolean expert;

    /** Whether the type should be hidden in menus of visual editors. */
    boolean hidden;

    /** Whether the type should be prioritized in menus of visual editors. */
    boolean preferred;

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
}

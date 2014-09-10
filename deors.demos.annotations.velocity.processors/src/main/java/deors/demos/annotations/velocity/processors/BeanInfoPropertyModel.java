package deors.demos.annotations.velocity.processors;

/**
 * Model for a property BeanInfo.
 *
 * @author deors
 * @version 1.0
 */
public class BeanInfoPropertyModel {

    /** Property name. */
    String name;
    
    /** Property qualified type. */
    String qualifiedType;
    
    /** Property description. */
    String description;
    
    /** Whether the property is an expert feature. */
    boolean expert;

    /** Whether the property should be hidden in menus of visual editors. */
    boolean hidden;
    
    /** Whether the property should be prioritized in menus of visual editors. */
    boolean preferred;

    /**
     * Getter for name property.
     *
     * @return the property value.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for qualifiedType property.
     *
     * @return the property value.
     */
    public String getQualifiedType() {
        return qualifiedType;
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

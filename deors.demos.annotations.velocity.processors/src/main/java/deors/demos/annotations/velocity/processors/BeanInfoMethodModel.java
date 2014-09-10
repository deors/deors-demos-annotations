package deors.demos.annotations.velocity.processors;

import java.util.List;

/**
 * Model for a method BeanInfo.
 *
 * @author deors
 * @version 1.0
 */
public class BeanInfoMethodModel {

    /** Method name. */
    String name;

    /** Method qualified return type. */
    String qualifiedType;

    /** Method parameters. */
    List<BeanInfoPropertyModel> parameters;

    /** Method description. */
    String description;

    /** Whether the method is an expert feature. */
    boolean expert;

    /** Whether the method should be hidden in menus of visual editors. */
    boolean hidden;

    /** Whether the method should be prioritized in menus of visual editors. */
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
     * Getter for parameters property.
     *
     * @return the property value.
     */
    public List<BeanInfoPropertyModel> getParameters() {
        return parameters;
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

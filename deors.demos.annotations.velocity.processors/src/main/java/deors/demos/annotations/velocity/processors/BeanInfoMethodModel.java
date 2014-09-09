package deors.demos.annotations.velocity.processors;

import java.util.List;

public class BeanInfoMethodModel {

    String name;
    String qualifiedType;
    List<BeanInfoPropertyModel> parameters;
    String description;
    boolean expert;
    boolean hidden;
    boolean preferred;

    public String getName() {
        return name;
    }
    public String getQualifiedType() {
        return qualifiedType;
    }
    public List<BeanInfoPropertyModel> getParameters() {
        return parameters;
    }
    public String getDescription() {
        return description;
    }
    public boolean isExpert() {
        return expert;
    }
    public boolean isHidden() {
        return hidden;
    }
    public boolean isPreferred() {
        return preferred;
    }
}

package deors.demos.annotations.velocity.processors;

public class BeanInfoPropertyModel {

    String name;
    String qualifiedType;
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

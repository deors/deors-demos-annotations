package deors.demos.annotations.velocity.processors;

public class BeanInfoModel {

    String packageName;
    String className;
    String qualifiedName;
    String beanInfoClassName;
    String beanInfoQualifiedName;
    String description;
    boolean expert;
    boolean hidden;
    boolean preferred;

    public String getPackageName() {
        return packageName;
    }
    public String getClassName() {
        return className;
    }
    public String getQualifiedName() {
        return qualifiedName;
    }
    public String getBeanInfoClassName() {
        return beanInfoClassName;
    }
    public String getBeanInfoQualifiedName() {
        return beanInfoQualifiedName;
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

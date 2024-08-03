package entities;

public class CategoryElement extends Entity{
    private String code;
    private String name;
    private String extension;


    public CategoryElement() {
    }
    public Category fetchCategoryOfCategoryElementSet(){
        return null;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}

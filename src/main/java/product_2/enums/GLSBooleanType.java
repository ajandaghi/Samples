package product_2.enums;

public enum GLSBooleanType {

    GLS_BOOLEAN_TRUE("GLS_BOOLEAN_TRUE", "بلی"),
    GLS_BOOLEAN_FALSE("GLS_BOOLEAN_FALSE", "خیر");

    GLSBooleanType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private final String code;
    private final String value;

    public String code() {
        return code;
    }

    public String value() {
        return value;
    }
}

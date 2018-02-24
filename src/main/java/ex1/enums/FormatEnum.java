package ex1.enums;

public enum FormatEnum {
    PLAIN("plain"),
    HTML("html");

    public String format;

    FormatEnum(String format) {
        this.format = format;
    }
}

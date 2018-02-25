package ex1.enums;

public enum ErrorCodesEnum {
    ERROR_UNKNOWN_WORD(1, "code=\"1\""),
    ERROR_REPEAT_WORD(2, "code=\"2\""),
    ERROR_CAPITALIZATION(3, "code=\"3\""),
    ERROR_TOO_MANY_ERRORS(4, "code=\"4\"");

    public String xmlValue;
    public int number;

    ErrorCodesEnum(int number, String xmlValue) {
        this.number = number;
        this.xmlValue = xmlValue;
    }
}

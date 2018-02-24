package ex1.enums;

public enum ParametersEnum {
    YANDEX_SPELLER_API_URI("https://speller.yandex.net/services/spellservice.json/checkText"),
    TEXT("text"),
    FORMAT("format"),
    OPTIONS("options"),
    LANG("lang"),
    CODE("\"code\":1");

    public String param;

    ParametersEnum(String param) {
        this.param = param;
    }
}

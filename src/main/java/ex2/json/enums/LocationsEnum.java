package ex2.json.enums;

public enum LocationsEnum {
    LOC_KEY("locations"),
    DEFAULT_LOCATION("9.7391536,-104.9847034"),
    SPECIAL_LOCATION("59.884187, 30.443366"),
    INVALID_LOCATION("djgkldc");

    public String loc;

    LocationsEnum(String loc) {
        this.loc = loc;
    }
}

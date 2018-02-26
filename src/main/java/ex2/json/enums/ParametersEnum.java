package ex2.json.enums;

public enum ParametersEnum {
    MAP_URI("url", "https://maps.googleapis.com/maps/api/elevation/json"),
    KEY("key", "AIzaSyAV3iY0KGpna2AOetLSpES7PPf3XDwSB2Q"),
    LOCATION("locations", null),
    KEY_ELEVATIONS("elevation", null),
    ERROR("error_message", null),
    BAD_STATUS( "status", "INVALID_REQUEST");

    public String tag;
    public String param;

    ParametersEnum(String tag, String param) {
        this.tag = tag;
        this.param = param;
    }
}

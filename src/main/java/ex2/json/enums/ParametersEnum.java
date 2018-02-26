package ex2.json.enums;

public enum ParametersEnum {
    MAP_URI("url", "https://maps.googleapis.com/maps/api/elevation/json"),
    KEY("key", "AIzaSyBV2nK5nbzLcmMUXfVtLyv7XPl_q_CmVUk"),
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

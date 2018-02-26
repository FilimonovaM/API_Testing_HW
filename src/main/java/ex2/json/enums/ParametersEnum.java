package ex2.json.enums;

public enum ParametersEnum {
    MAP_URI("url", "https://maps.googleapis.com/maps/api/elevation/json"),
    KEY("key", "AIzaSyCkhO6O9bcWchaLhEzE6w3kA4jir6EESEY"),
    LOCATION("locations", null),
    KEY_ELEVATIONS("elevation", null),
    ERROR("error_message", null),
    BAD_STATUS( "status", "INVALID_REQUEST");

    public String name;
    public String param;

    ParametersEnum(String name, String param) {
        this.name = name;
        this.param = param;
    }
}

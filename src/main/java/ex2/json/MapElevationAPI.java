package ex2.json;

import beans.GoogleMapsElevationAPI;
import beans.YandexSpellerAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ex2.json.enums.ParametersEnum;
import ex1.rest.YandexSpellerApi;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static ex2.json.enums.LocationsEnum.DEFAULT_LOCATION;
import static ex2.json.enums.ParametersEnum.KEY;
import static ex2.json.enums.ParametersEnum.LOCATION;
import static ex2.json.enums.ParametersEnum.MAP_URI;

public class MapElevationAPI {

    private MapElevationAPI() {

    }

    private HashMap<String, String> params = new HashMap<>();

    public static class ApiBuilder {
        MapElevationAPI elevationAPI;

        private ApiBuilder(MapElevationAPI gc) {
            elevationAPI = gc;
        }

        public MapElevationAPI.ApiBuilder locations() {
            elevationAPI.params.put(LOCATION.name, DEFAULT_LOCATION.loc);
            return this;
        }

        public Response callApi() {
            return prepareApi().post(MAP_URI.param).prettyPeek();
        }

        public RequestSpecification prepareApi() {
            return RestAssured.with()
                    .queryParams(elevationAPI.params)
                    .and().auth().basic(KEY.name, KEY.param)
                    .log().all();
        }
    }

    public static MapElevationAPI.ApiBuilder with() {
        MapElevationAPI api = new MapElevationAPI();
        return new MapElevationAPI.ApiBuilder(api);
    }

    public static List<GoogleMapsElevationAPI> getMapElevations(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<GoogleMapsElevationAPI>>() {
        }.getType());
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(Matchers.lessThan(20000L)).expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .addHeader("header", "header.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(MAP_URI.param)
                .build();
    }
}

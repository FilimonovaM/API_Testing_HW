package ex2.json;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static ex2.json.enums.LocationsEnum.DEFAULT_LOCATION;
import static ex2.json.enums.LocationsEnum.LOC_KEY;
import static ex2.json.enums.ParametersEnum.*;

public class GoogleMapsElevatorTest {

    @Test
    public void simpleAPITest() {
        RestAssured
                .with()
                .queryParam(LOC_KEY.loc, DEFAULT_LOCATION.loc)
                .and().auth().basic(KEY.tag, KEY.param)
                .accept(ContentType.JSON)
                .log().everything()
                .when()
                .get(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().contentType(ContentType.JSON)
                .assertThat().statusCode(HttpStatus.SC_OK)
                .assertThat().body(Matchers.containsString(KEY_ELEVATIONS.tag));
    }

    @Test
    public void useBasicCallApi() {
        MapElevationAPI
                .with()
                .locations()
                .callApi()
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void checkErrorMessage() {
        MapElevationAPI
                .with()
                .callApi()
                .then()
                .assertThat().body(Matchers.containsString(BAD_STATUS.tag))
                .assertThat().body(Matchers.containsString(BAD_STATUS.param))
                .assertThat().body(Matchers.containsString(ERROR.tag));
    }


    @Test
    public void useBasicRequests() {
        //get
        MapElevationAPI
                .with()
                .locations()
                .prepareApi()
                .get(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //post
        MapElevationAPI
                .with()
                .locations()
                .prepareApi()
                .post(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //delete
        MapElevationAPI
                .with()
                .locations()
                .prepareApi()
                .delete(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);

        //head
        MapElevationAPI
                .with()
                .locations()
                .prepareApi()
                .head(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //options
        MapElevationAPI
                .with()
                .locations()
                .prepareApi()
                .options(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //put
        MapElevationAPI
                .with()
                .locations()
                .prepareApi()
                .put(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);

        //patch
        MapElevationAPI
                .with()
                .locations()
                .prepareApi()
                .patch(MAP_URI.param)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}

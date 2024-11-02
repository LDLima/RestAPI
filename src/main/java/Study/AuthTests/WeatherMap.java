package Study.AuthTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WeatherMap {
    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "https://api.openweathermap.org/data/2.5/weather";

        RequestSpecBuilder req = new RequestSpecBuilder();
        req.setContentType(ContentType.JSON);
        req.log(LogDetail.ALL);
        reqSpec = req.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.log(LogDetail.ALL);
        resSpec = resBuilder.build();

        requestSpecification = reqSpec;
        responseSpecification = resSpec;
    }


    @Test
    public void accessWeatherMapAPI_WithAccount(){
        given()
                .queryParam("q", "Porto,PT")
                .queryParam("appid","227b01fda24373a58d6191ca3db38cd1")
                .queryParam("units", "metric")
        .when()
                .get("")
        .then()
                .statusCode(200)
                .body("name", is("Porto"))
                .body("coord.lon", is(-8.611f))
                .body("main.temp", greaterThan(21f))
        ;
    }

}

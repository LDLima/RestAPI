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
import static org.hamcrest.Matchers.is;

public class StarWars {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "https://swapi.dev/api";

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
    public void accessStarWarsAPI(){
        given()
        .when()
                .get("/people/1")
        .then()
                .statusCode(200)
                .body("name", is("Luke Skywalker"))
        ;
    }
}

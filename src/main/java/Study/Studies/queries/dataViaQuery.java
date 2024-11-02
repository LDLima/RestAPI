package Study.Studies.queries;

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

public class dataViaQuery {
    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "https://restapi.wcaquino.me";

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
    public void queryInsert(){
        given()
        .when()
                .get("/v2/users?format=json") //Sending parameters for the query. But this is not the real way to send it. Here is just getting the data and sending
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
        ;
    }

    @Test
    public void queryInsertViaParams(){
        given()
                .queryParam("format", "json")//Sending parameters for the query. Look the log Query Params
                .queryParam("anything", "toIgnore")
        .when()
                .get("/v2/users")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentType(containsString("utf-8")) //Verify. Maybe could be useful
        ;
    }

    @Test
    public void queryInsertViaHeader(){
        given()
                .accept(ContentType.HTML)//Only accept that type when receiving the response - Not what you're sending
        .when()
                .get("/v2/users")
        .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
        ;
    }
}

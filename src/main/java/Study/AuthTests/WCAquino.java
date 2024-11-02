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

public class WCAquino {
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
    public void confirmPassIsNeeded(){
        given()
        .when()
                .get("/basicauth")
        .then()
                .statusCode(401)
        ;
    }

    @Test
    public void auth(){
        given()
                .auth().basic("admin", "senha")
        .when()
                .get("/basicauth")
//                .get("https://admin:senha@restapi.wcaquino.me/basicauth")//Stupid way, please do never do that in a real test; API's must not allow
        .then()
                .statusCode(200)
                .body("status", is("logado"))
        ;
    }

    @Test
    public void authBasicChallengePreemptive(){//Autenticação preemptiva
        given()
                .auth().preemptive().basic("admin", "senha")
        .when()
                .get("/basicauth2")
        .then()
                .statusCode(200)
                .body("status", is("logado"))
        ;
    }

}

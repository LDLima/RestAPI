package Study.AuthTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BarrigaRest {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "https://barrigarest.wcaquino.me/";

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
    public void confirmPassIsNeeded(){ //Login - Token - Contas
        Map<String, String> login = new HashMap<>();
        login.put("email", "luan.domingues@veepee.com");
        login.put("senha", "1111");

        String token = given()
                .body(login)
        .when()
                .post("/signin")
        .then()
                .statusCode(200)
                .extract().path("token")
        ;

        given()
                .header("Authorization", "JWT " + token)
        .when()
                .get("/contas")
        .then()
                .statusCode(200)
                .body("nome", hasItem("Test"))
        ;
    }
}

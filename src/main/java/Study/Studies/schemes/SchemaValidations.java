package Study.Studies.schemes;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
/*
    Verifying schemes, makin sure what we have in a large Json is being respected the format we want
 */
public class SchemaValidations {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "http://restapi.wcaquino.me";

        RequestSpecBuilder req = new RequestSpecBuilder();
        req.setContentType(ContentType.JSON);
//        req.log(LogDetail.ALL);
        reqSpec = req.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.log(LogDetail.ALL);
        resSpec = resBuilder.build();

        requestSpecification = reqSpec;
        responseSpecification = resSpec;
    }

    @Test
    public void validatingSchemeJson(){
        given()
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/users.json"))//If I remove classpath it fails, why? Order of the files?
        ;
    }
}

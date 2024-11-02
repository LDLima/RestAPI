package Study.Studies.possibilitesAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OptionsApi {
    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;


    @BeforeClass
    public static void setup(){
        baseURI = "https://restapi.wcaquino.me";

        RequestSpecBuilder req = new RequestSpecBuilder();
        req.setContentType("application/json");
        req.log(LogDetail.ALL);
        reqSpec = req.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
//        resBuilder.expectStatusCode(201);
        resBuilder.log(LogDetail.ALL);
        resSpec = resBuilder.build();

        requestSpecification = reqSpec;
        responseSpecification = resSpec;
    }


    @Test
    public void saveUser(){
        given()
                /*
                    Is mandatory to inform which type of data you are sending;
                 */
                .contentType("application/json")
                .body("  {" +
                        "        \"name\": \"Wolverine\",\n" +
                        "        \"age\":200\n" +
                        "    }")
        .when()
                .post("/users")
        .then()
                .body("id", is(notNullValue()))
                .body("age", greaterThan(70))
                .body("name", is("Wolverine"))
        ;
    }

    @Test
    public void verifyRrequiredFields(){
        given()
                .contentType("application/json")
                .body("  {" +
                        "        \"age\":200\n" +
                        "    }")
        .when()
                .post("/users")
        .then()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"))
        ;
    }

    @Test
    public void putUser(){
        given()
                .body("  {" +
                        "        \"name\": \"Wolverine the II\",\n" +
                        "        \"age\":200\n" +
                        "    }")
        .when()
                .put("/users/1") //User you want to change
        .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Wolverine the II"))
        ;
    }

    @Test
    public void putUserCustomized(){
        given()
                .body("  {" +
                        "        \"name\": \"Wolverine the II\",\n" +
                        "        \"age\":200\n" +
                        "    }")
                .pathParam("entity", "users")
                .pathParam("userId", 1)
        .when()
                .put("/{entity}/{userId}") //Sending customized values
        .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Wolverine the II"))
        ;
    }

    @Test
    public void deleteUser(){
        given()
                .pathParam("entity", "users")
                .pathParam("userId", 1)
        .when()
                .delete("/{entity}/{userId}")
        .then()
                .statusCode(204)
        ;
    }

    @Test
    public void deleteInexistentUser(){
        given()
                .pathParam("entity", "users")
                .pathParam("userId", 100)
        .when()
                .delete("/{entity}/{userId}")
        .then()
                .statusCode(400)
                .body("error", is("Registro inexistente"))
        ;
    }
}

package Study.Studies.organized;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class Organized {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "https://restapi.wcaquino.me";
        port = 443;
//        basePath = "/v2";

         /*
            Build up all the details for the make the request in the given/when/then
            This is used to make a standard pattern
         */
        RequestSpecBuilder req = new RequestSpecBuilder();
        req.log(LogDetail.ALL);
        reqSpec = req.build();

        /*
            Also created a standard method for the response as well
         */
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(200);
        resSpec = resBuilder.build();

        /*
            If you add the following lines, you won't need to add anymore in any of the given.when.then();
            The RestAssured already will assume that and it will know that this specifics values are to be verified
            In every query you have;
         */
        requestSpecification = reqSpec;
        responseSpecification = resSpec;
    }

    @Test
    public void organizedTest(){
        given()
                .log().all()
        .when()
                .get("/users")//Now we just put the specific part that we want to validate
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void requestResponseBuilder(){
        /*
            Once created, now you invoke to use all the setup you made it before
         */
        given()
                .spec(reqSpec) //All the setups already defined.
        .when()
                .get("/users")
        .then()
                .statusCode(200) //Remove this for all the ones and just add the line below;
                .spec(resSpec)
        ;

    }

    @Test
    public void requestResponseBuilderWithGlobalVariable(){
        given()
                //Here enter the requestSpecification
        .when()
                .get("/users")
        .then()
                //Here enter the responseSpecification
        ;

    }
}

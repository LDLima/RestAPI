package Study.Studies.maps;

import Study.Objects.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class APIUsingMap {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "https://restapi.wcaquino.me";

        RequestSpecBuilder req = new RequestSpecBuilder();
        req.setContentType("application/json");
//        req.log(LogDetail.ALL);
        reqSpec = req.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.log(LogDetail.ALL);
        resSpec = resBuilder.build();

        requestSpecification = reqSpec;
        responseSpecification = resSpec;
    }

    @Test
    public void saveUser(){
        Map<String, Object> params = new HashMap<>();

        params.put("name", "Deadpool");
        params.put("age", 70);
        params.put("Salary", 57.324f);

        given()
                .body(params)//RestAssured already convert this to json, since we already define the contenty type, and that's it.
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("age", greaterThan(69))
                .body("name", is("Deadpool"))
        ;
    }

    @Test
    public void saveUser_byObject(){
        User user = new User("Deadpool", 70);

        given()
                .body(user)//Send the whole object nows
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("age", greaterThan(69))
                .body("name", is("Deadpool"))
        ;
    }

    @Test
    /*
        Inserting a user
        Than veryfing that I inserted
        Getting the value and comparing it;
     */
    public void getInfo_ByObject(){
        User user = new User("Deadpool", 70);

        User insertedUser = given()
                .body(user)
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .extract().body().as(User.class) //Sending the type of class want to be extracted as;
        ;
        assertThat(insertedUser.getId(), is(notNullValue()));
        assertEquals("Deadpool", insertedUser.getName());
        assertThat(insertedUser.getAge(), is(70));
    }
}

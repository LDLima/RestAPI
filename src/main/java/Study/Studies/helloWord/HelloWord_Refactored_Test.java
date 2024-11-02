package Study.Studies.helloWord;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class HelloWord_Refactored_Test {
    //JUnit
        //Fail: When it's assertion error;
        //Error: An exception is trhow
    @Test
    public void apiVerification(){
        Response resp = request(Method.GET, "https://restapi.wcaquino.me/ola");

        assertEquals("Ola Mundo!", resp.getBody().asString());
        assertEquals(200, resp.statusCode());
        assertEquals("Adding here some error message for the case that if fails",
                200, resp.statusCode());
        assertEquals(200, resp.statusCode());

        ValidatableResponse validation = resp.then();
        validation.statusCode(200);
    }

    @Test
    public void otherPossibilitiesRest(){
//        get("https://restapi.wcaquino.me/ola")
//                .then()
//                .statusCode(200);

        given()
        .when()
            .get("https://restapi.wcaquino.me/ola")
        .then()
            .statusCode(200);
    }

    @Test
    public void matchersHamcrest(){
        assertThat("Maria", is("Maria"));
        assertThat(128, is(128));
        assertThat(128, isA(Integer.class));
        assertThat(128d, isA(Double.class));
        assertThat(128, greaterThan(120));
        assertThat(128, lessThan(130));

        List<Integer> impress = Arrays.asList(1,3,5,7,9);
        assertThat(impress, hasSize(5));
        assertThat(impress, contains(1,3,5,7,9));
        assertThat(impress, containsInAnyOrder(5,7,9,1,3));
        assertThat(impress, hasItem(7));
        assertThat(impress, hasItems(5,3,7));

        assertThat("Maria", is(not("João")));
        assertThat("Maria", not("João"));

        assertThat("Maria", anyOf(is("Maria"), is("João"))); //Ou
        assertThat("Maria", allOf(startsWith("Mar"), endsWith("ia"), containsString("aria"))); //E
    }

    @Test
    public void bodyValidation(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/ola")
        .then()
            .statusCode(200)
            // If you do not send any information for the Matchers in Hamcrest, the system already gets that you want to validate the whole body;
            .body(is(not(nullValue())))
            .body(containsString("Ola"))//I can add as many validations as I want.
            .body(containsString("Ola Mundo!"));
    }
}

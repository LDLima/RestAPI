package Challenge.UdemyCourse.specs;

import Challenge.UdemyCourse.base.BaseTest;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class LoginSpec extends BaseTest {

    @Test
    public void accessWithoutToken(){
        FilterableRequestSpecification remove = (FilterableRequestSpecification) requestSpecification;
        remove.removeHeader("Authorization");//Remove for the request for all the tests, not only for this one.

        given()
        .when()
                .get("/contas")
        .then()
                .statusCode(401);
        ;
    }
}

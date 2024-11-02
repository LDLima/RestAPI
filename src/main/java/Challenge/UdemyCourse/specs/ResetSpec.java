package Challenge.UdemyCourse.specs;

import Challenge.UdemyCourse.base.BaseTest;
import io.restassured.RestAssured;
import org.junit.Test;

public class ResetSpec extends BaseTest {
    @Test
    public void reset(){
        RestAssured.get("/reset")
        .then()
                .log().all()
                .statusCode(200)
        ;
    }
}

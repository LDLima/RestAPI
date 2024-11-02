package Challenge.UdemyCourse.suites;

import Challenge.UdemyCourse.auth.Token;
import Challenge.UdemyCourse.base.BaseTest;
import Challenge.UdemyCourse.specs.*;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountsSpec.class,
        ResetSpec.class,
        ControlledSpec.class,
        TransactionsSpec.class,
        AmountSpec.class,
        LoginSpec.class
})
public class ControlledSuite extends BaseTest {

    @BeforeClass
    public static void token(){
        addToken(setupToken()); //Need to add the token for all the classes. That's why it's here.
    }

    private static String setupToken(){
        Map<String, String> login = new HashMap<>();
        login.put("email", "luandevelop@gmail.com");
        login.put("senha", "TesteAquino");

        return given()
                .body(login)
                .when()
                .post("/signin")
                .then()
                .statusCode(200)
                .extract().path("token")
                ;
    }

    private static void addToken(String token){//FIX
        Token token1 = new Token(token);
        requestSpecification.header("Authorization", "JWT " + token1.getToken());
    }
}

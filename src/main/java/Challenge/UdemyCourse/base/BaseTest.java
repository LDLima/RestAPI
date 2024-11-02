package Challenge.UdemyCourse.base;

import Challenge.UdemyCourse.auth.Token;
import Challenge.UdemyCourse.config.Setup;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.*;

public class BaseTest implements Setup {

    public static Token token;

    @BeforeClass
    public static void setup(){
        setupURL();
        setupRequest();
        setupResponse();
        setupLogInFailScenario();
    }

    private static void setupURL(){
        switch (TESTING_URL) {
            case BASE_PORT: setupBaseURL(); break;
            case BASE_HTTPS: setupHTTPSURL(); break;
        }
        basePath = BASE_PATH;
    }

    private static void setupBaseURL(){
        baseURI = API_BASE_URL;
        port = API_BASE_PORT;
    }

    private static void setupHTTPSURL(){
        baseURI = BASE_URL_HTTPS;
        port = PORT_HTTPS;
    }

    private static void setupRequest(){
        RequestSpecBuilder req = new RequestSpecBuilder();
        req.setContentType(CONTENT_TYPE);
        requestSpecification = req.build();
    }

    private static void setupResponse(){
        ResponseSpecBuilder res = new ResponseSpecBuilder();
        res.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        responseSpecification = res.build();
    }

    private static void setupLogInFailScenario(){
        enableLoggingOfRequestAndResponseIfValidationFails();
    }
}

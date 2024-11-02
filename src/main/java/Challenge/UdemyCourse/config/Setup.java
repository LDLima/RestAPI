package Challenge.UdemyCourse.config;

import io.restassured.http.ContentType;

import static Challenge.UdemyCourse.config.PortURL.*;

public interface Setup {
    PortURL TESTING_URL =
//            BASE_PORT;
            BASE_HTTPS;

    String API_BASE_URL = "http://barrigarest.wcaquino.me";
    Integer API_BASE_PORT = 80;

    String BASE_URL_HTTPS = "https://barrigarest.wcaquino.me/";
    Integer PORT_HTTPS = 443;

    String BASE_PATH = ""; //When needed /api/v1, /api/v2

    ContentType CONTENT_TYPE = ContentType.JSON;
    Long MAX_TIMEOUT = 5000L;
}

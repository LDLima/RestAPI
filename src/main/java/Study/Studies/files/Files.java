package Study.Studies.files;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Files {
    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup(){
        baseURI = "http://restapi.wcaquino.me";

        RequestSpecBuilder req = new RequestSpecBuilder();
//        req.setContentType(ContentType.JSON);
//        req.log(LogDetail.ALL);
        reqSpec = req.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
//        resBuilder.log(LogDetail.ALL);
        resSpec = resBuilder.build();

        requestSpecification = reqSpec;
        responseSpecification = resSpec;
    }

    @Test
    public void sendRequiredFile(){
        given()
        .when()
                .post("/upload")
        .then()
                .statusCode(404)
                .body("error", containsString("não enviado"))
        ;
    }

    @Test
    public void sendFile(){
        given()
                /*
                    Sending the route and the path of the archive
                    Cannot use multpart with contentType;
                    File with ZeroBytes is not sending as well - nullCase
                 */
                .multiPart("arquivo", new File("src/main/java/Study/resources/files/Pass_Users_PRE.sql"))
        .when()
                .post("/upload")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void sendBiggerThanExpectedFile(){
        given()
                .multiPart("arquivo", new File("src/main/java/Study/resources/files/Dummy.zip"))
        .when()
                .post("/upload")
        .then()
                .time(lessThan(700L))//Over here is the definition of time. The top; But this is not performance
                .statusCode(413)
        ;
    }

    @Test
    public void downloadFile() throws IOException {
        byte[] byteArray = given()
                .when()
                    .get("/download")
                .then()
                    .statusCode(200)
                    .extract().asByteArray();//extract as a byte array

        File image =  new File("src/main/java/Study/resources/download/file.jpg"); //Where to save the file.
        OutputStream out = new FileOutputStream(image); //To write and save the file, because of java
        out.write(byteArray);
        out.close();

        Assert.assertThat(image.length(), lessThan(100000L));//How to save twice?
    }
}

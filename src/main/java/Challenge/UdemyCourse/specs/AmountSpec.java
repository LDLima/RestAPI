package Challenge.UdemyCourse.specs;

import Challenge.UdemyCourse.base.BaseTest;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AmountSpec extends BaseTest {
    @Test
    public void totalAccountsMoney(){
        Integer conta_id = getAccountId("Conta para saldo");
        given()
        .when()
                .get("/saldo")
        .then()
                .statusCode(200)
                .body("find{it.conta_id == "+conta_id+"}.saldo", is("534.00"))
        ;
    }

    public Integer getAccountId(String name){
        return get("/contas?nome="+name)
                .then().extract().path("id[0]");
    }
}

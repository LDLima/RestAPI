package Challenge.UdemyCourse.specs;

import Challenge.UdemyCourse.base.BaseTest;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class ControlledSpec extends BaseTest {

    @Test
    public void addAccount(){
        given()
                .body("{ \"nome\": \"Conta inserida\" }")
        .when()
                .post("/contas")
        .then()
                .statusCode(201)
        ;
    }

    @Test
    public void editAccount(){
        Integer conta_id = getAccountId("Conta para alterar");
        given()
                .body("{ \"nome\": \"Conta alterada\" }")
                .pathParam("id",conta_id)
        .when()
                .put("/contas/{id}")//Stop sendin the precisely number. Make an id
        .then()
                .statusCode(200)
                .body("nome", is("Conta alterada"))
        ;
    }

    public Integer getAccountId(String name){ //FIX
        return get("/contas?nome="+name)
                .then().extract().path("id[0]");
    }

    @Test
    public void addRepitedNameAccount(){
        given()
                .body("{ \"nome\": \"Conta mesmo nome\" }")
        .when()
                .post("/contas")
        .then()
                .statusCode(400)
                .body("error", is("Já existe uma conta com esse nome!"))
        ;
    }
}

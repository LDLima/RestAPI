package Challenge.UdemyCourse.specs;

import Challenge.UdemyCourse.base.BaseTest;
import Challenge.UdemyCourse.objects.Account;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static Challenge.UdemyCourse.steps.AccountSteps.getData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountsSpec extends BaseTest {

    private static String accountName = "Account " + System.nanoTime();
    private static Integer conta_id;
    private static Integer transaction_id;

    @Test
    public void t01_addAccount(){
        conta_id = given()
                .body("{ \"nome\": \""+accountName+"\" }")
        .when()
                .post("/contas")
        .then()
                .statusCode(201)
                .extract().path("id")
        ;
    }


    @Test
    public void t02_editAccount(){
        given()
                .body("{ \"nome\": \""+accountName+" NuConta\" }")
                .pathParam("id", conta_id)
        .when()
                .put("/contas/{id}")//Stop sendin the precisely number. Make an id
        .then()
                .statusCode(200)
                .body("nome", is(accountName+ " NuConta"))
        ;
    }

    @Test
    public void t03_addRepitedNameAccount(){
        given()
                .body("{ \"nome\": \""+accountName+" NuConta\" }")
        .when()
                .post("/contas")
        .then()
                .statusCode(400)
                .body("error", is("Já existe uma conta com esse nome!"))
        ;
    }

    @Test
    public void t04_addMoney(){
        Account account = new Account();
        account.setConta_id(conta_id);
        account.setDescricao("Adicionando");
        account.setEnvolvido("Envolvido");
        account.setTipo("REC");
        account.setData_transacao(getData(-1));
        account.setData_pagamento(getData(5));
        account.setValor(100F);
        account.setStatus(true);

        transaction_id = given()
                .body(account)
        .when()
                .post("/transacoes")
        .then()
                .statusCode(201)
                .extract().path("id")
        ;
    }


    @Test
    public void t05_requiredFieldsValidation(){
        given()
                .body("{}")
        .when()
                .post("/transacoes")
        .then()
                .statusCode(400)
                .body("$", hasSize(8))
                .body("msg", hasItems(containsString("é obrigatório")))
        ;
    }

    @Test
    public void t06_addMoneyInFuture(){
        Account account = new Account();
        account.setConta_id(conta_id);
        account.setDescricao("Adicionando");
        account.setEnvolvido("Envolvido");
        account.setTipo("REC");
        account.setData_transacao(getData(2));
        account.setData_pagamento(getData(5));
        account.setValor(100F);
        account.setStatus(true);

        given()
                .body(account)
        .when()
                .post("/transacoes")
        .then()
                .statusCode(400)
                .body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual")) //Difference between hasItem and is from Matchers.
        ;
    }


    @Test
    public void t07_removeAccountWithTransactions(){
        given()
                .pathParam("id", conta_id)
        .when()
                .delete("/contas/{id}")
        .then()
                .statusCode(500)
                .body("constraint", is("transacoes_conta_id_foreign"))
        ;
    }

    @Test
    public void t08_totalAccountsMoney(){ //How to do dynamic with a lot of values?
        given()
        .when()
                .get("/saldo")
        .then()
                .statusCode(200)
                .body("find{it.conta_id == "+conta_id+"}.saldo", is("100.00"))
        ;
    }

    @Test
    public void t09_removeTransaction(){ //How to do dynamic with a lot of values?
        given()
                .pathParam("id", transaction_id)
        .when()
                .delete("/transacoes/{id}")
        .then()
                .statusCode(204)
        ;
    }

}


package Challenge.UdemyCourse.specs;

import Challenge.UdemyCourse.base.BaseTest;
import Challenge.UdemyCourse.objects.Account;
import org.junit.Test;

import static Challenge.UdemyCourse.steps.AccountSteps.getData;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TransactionsSpec extends BaseTest {

    @Test
    public void addMoney(){
        Account account = new Account();
        account.setConta_id(getAccountId("Conta para movimentacoes"));
        account.setDescricao("Adicionando");
        account.setEnvolvido("Envolvido");
        account.setTipo("REC");
        account.setData_transacao(getData(-1));
        account.setData_pagamento(getData(5));
        account.setValor(100F);
        account.setStatus(true);

        given()
                .body(account)
        .when()
                .post("/transacoes")
        .then()
                .statusCode(201)
        ;
    }

    @Test
    public void requiredFieldsValidation(){
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
    public void addMoneyInFuture(){
        Account account = new Account();
        account.setConta_id(getAccountId("Conta para movimentacoes"));
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
                .body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
        ;
    }

    @Test
    public void removeAccountWithTransactions(){
        given()
                .pathParam("id", getAccountId("Conta com movimentacao"))
        .when()
                .delete("/contas/{id}")
        .then()
                .statusCode(500)
                .body("constraint", is("transacoes_conta_id_foreign"))
        ;
    }

    @Test
    public void removeTransaction(){
        Integer transaction_id = getAccountTransctionId("Movimentacao para exclusao");
        given()
                .pathParam("id", transaction_id)
        .when()
                .delete("/transacoes/{id}")
        .then()
                .statusCode(204)
        ;
    }


    public Integer getAccountId(String name){
        return get("/contas?nome="+name)
                .then().extract().path("id[0]");
    }

    public Integer getAccountTransctionId(String desc){
        return get("/transacoes?descricao="+desc)
                .then().extract().path("id[0]");
    }
}

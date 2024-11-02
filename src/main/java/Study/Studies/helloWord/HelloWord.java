package Study.Studies.helloWord;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class HelloWord {
    public static void main(String[] args) {
        Response resp = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
        /*
        //Algumas vezes é necessário passar a porta, pois pode dar erro de connection refused.
        String test = resp.getBody().asString();
        System.out.println(test);
        /*
            Não há cache aqui. Eu não preciso ficar preocupado com o que se está se pedindo estar cacheado
            Se fizer outra requisição verá que sempre vem o 200, não o 304 de status code.

        System.out.println(resp.statusCode());
        */
        System.out.println(resp.statusCode()==200);
        System.out.println(resp.getBody().asString().equals("Ola Mundo!"));

        ValidatableResponse validation = resp.then();
        validation.statusCode(201);
    }
}

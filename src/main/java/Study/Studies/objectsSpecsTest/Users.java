package Study.Studies.objectsSpecsTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Users {
    @Test
    public void verifyOneLevel(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/1")
        .then()
                .statusCode(200)
                .body("id", is(1))
                .body("age", is(lessThan(35)))
                .body("name", containsString("Silva"));

    }

    @Test
    public void verifyOneLevelAlternative(){
        Response resp = get("https://restapi.wcaquino.me/users/1");

        assertEquals(Integer.valueOf(1), resp.path("id"));
        assertEquals(Integer.valueOf(1), resp.path("%s","id"));//Está passando via string um parâmetro, e qual o parâmetro dentro do body? Id;

        //Json Path
        JsonPath json = new JsonPath(resp.asString());
       assertEquals(1, json.getInt("id"));

        int id = JsonPath.from(resp.asString()).getInt("id");
        assertEquals(1, id);
    }

    @Test
    public void verifySecondLevel(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/2")
        .then()
                .statusCode(200)
                .body("age", is(lessThan(35)))
                .body("endereco.rua", containsString("bobos"))
                .body("name", containsString("Maria"));
    }

    @Test
    public void getListInsideUser(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/3")
        .then()
                .statusCode(200)
                .body("age", is(lessThan(35)))
                .body("name", containsString("Ana"))
                .body("filhos", hasSize(2))
                .body("filhos[0].name", containsString("Zez"))
                .body("filhos[1].name", containsString("uiz"))
                .body("filhos.name", hasItem("Zezinho")) //Give back a list with the elements
                .body("filhos.name", hasItems("Zezinho", "Luizinho")) //Give back a list with the elements
        ;
    }

    @Test
    public void getErrors(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/4")
        .then()
                .statusCode(404)
                .body("error", containsString("inexistente"))
        ;
    }

    @Test
    public void verifyListAtRootPath(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/")
        .then()
                .statusCode(200)
                .body("$", hasSize(3))//This means in the root;
                .body("", hasSize(3))//This also means in the root;
                .body("name", hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))//Getting all the items that have this attribute
                .body("name", hasItems(containsString("João"), containsString("Maria"), containsString("Ana")))
                .body("age[1]", is(25))
                .body("filhos.name", hasItems(Arrays.asList("Zezinho", "Luizinho"))) //What if wnat to check just one?
//                .body("salary", contains(2500))
        ;
    }

    @Test
    public void advancedVerification(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/")
        .then()
                /*
                    It is for the foreach; A type of the info that is receiving from the api;
                    And the 2 is the comparison that is making.
                    From the first one, get the data. The second argument is the one to verify;
                    Between the {} you're defining what you want - the arguments you want.
                 */
                .statusCode(200)
                .body("$", hasSize(3))
                .body("age.findAll{it <= 25}.size()", is(2))
                .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                /*
                    Without sending nothing, the find all is going to the json root and get all the info from it.
                    Then the it becomes the whole info of each json element
                    In the first one below, it's getting its name.
                 */
                .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina")) //needs to be hasItem, because findAll returns a list.
                .body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))// This is to make the treatment, getting the first item of the list
                .body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))// T-1 start the list from the last item to the top
                .body("find{it.age <= 25 && it.age > 20}.name", is("Maria Joaquina")) //Find returns only the first element that it finds.
                .body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia")) //Find the name of the people who contains n
                .body("findAll{it.name.length() > 10}.name ", hasItems("João da Silva", "Maria Joaquina"))
                .body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA")) //Change the response, do some treatment with the data
                .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))//Change the response, after find them all that starts with Maria and then do some treatment with the data
                /*
                    Make sure is getting the data adn do a second verification on it.
                    Steps:
                        Transform in array first.
                        Then verify if the array contains
                 */
                .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
                .body("age.collect{it * 2}", hasItems(60,50,40))//Treatment with the data.
                .body("id.max()", is(3)) //The top id of the json (get the Id and verify the max of them)
                .body("salary.min()", is(1234.5678f))//The min salary, almost like the last verification
//                .body("salary.sum()", is(3734.5678f))//Make the sum of all salaries. It fails if it sends like this. Need to deal with the empty salary
                /*
                    Get all the salaries that are not null and make the sum;
                    But needs to verify the response. The close to is the "Margem de erro"/error scale for the sum with three decimals case.
                 */
                .body("salary.findAll{ it != null}.sum()", is(closeTo(3734.5678f, 0.001)))
                .body("salary.findAll{ it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)))
                /*
                    All this validations above come for the Groovy.
                 */

        ;
    }

    /*
        Uniting with JsonPath. Puts all in a variable and do the comparison for the test above easier to write and read.
     */
    @Test
    public void advancedVerificationEasier(){
        ArrayList<String> names = given()
                                .when()
                                        .get("https://restapi.wcaquino.me/users/")
                                .then()
                                        .statusCode(200)
                                        /*
                                            Extract everything, given the path.
                                            Easier, better, and you can do in java, not in the hamcrest.
                                         */
                                        .extract().path("name.findAll{it.startsWith('Maria')}");
        //Java again
        Assert.assertEquals(1, names.size());
        Assert.assertTrue(names.get(0).equalsIgnoreCase("Maria joaquina"));
        Assert.assertEquals(names.get(0).toUpperCase(), "MARIA JOAQUINA");
    }
}

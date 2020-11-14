package br.com.asilva.rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserXMLTest {

    @Test
    public void devoTrabalharComXML(){
        given()
        .when()
            .get("http://restapi.wcaquino.me/usersXML/3")
        .then()
            .statusCode(200)
            .rootPath("user")
            .body("name", is("Ana Julia"))
            .body("@id", is("3"))
            .rootPath("user.filhos")
            .body("name.size()", is(2))
            .body("name[0]", is("Zezinho"))
            .body("name[1]", is("Luizinho"))
            .body("name", hasItem("Luizinho"))
            .body("name", hasItems("Luizinho", "Zezinho"))

        ;
    }
}

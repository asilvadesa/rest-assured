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
            .body("user.name", is("Ana Julia"))
            .body("user.@id", is("3"))
            .body("user.filhos.name.size()", is(2))
            .body("user.filhos.name[0]", is("Zezinho"))
            .body("user.filhos.name[1]", is("Luizinho"))
            .body("user.filhos.name", hasItem("Luizinho"))
            .body("user.filhos.name", hasItems("Luizinho", "Zezinho"))

        ;
    }
}

package br.com.asilva.rest;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class VerbosTest {

    @Test
    public void deveSalvarUsuario(){
        given()
            .log().all()
                .contentType("application/json")
                .body("{\"name\": \"Jose\",\"age\": 50}")
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("name", is("Jose"))
            .body("age", is(50))
        ;
    }

    @Test
    public void naoDeveSalvarUsuarioSemNome(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"age\": 50}")
        .when()
                .post("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"));

    }

    @Test
    public void deveSalvarUsuarioViaXML(){
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body("<user><name>Jose</name><age>50</age></user>")
        .when()
                .post("https://restapi.wcaquino.me/usersXML")
        .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Jose"))
                .body("user.age", is("50"));

    }

    @Test
    public void deveAlterarUsuario(){
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"Usuario Alterado\",\"age\": 80}")
        .when()
            .put("https://restapi.wcaquino.me/users/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(1))
            .body("name", is("Usuario Alterado"))
            .body("age", is(80))
            .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void devoCustomizarURLPart1(){
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Usuario Alterado\",\"age\": 80}")
        .when()
                .put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
       .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Usuario Alterado"))
                .body("age", is(80))
                .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void devoCustomizarURLPart2(){
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Usuario Alterado\",\"age\": 80}")
                .pathParam("entidade", "users")
                .pathParam("userId", 1)
        .when()
                .put("https://restapi.wcaquino.me/{entidade}/{userId}")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Usuario Alterado"))
                .body("age", is(80))
                .body("salary", is(1234.5678f))
        ;
    }
}


package br.com.asilva.rest;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarWeatherAPIcomChave(){
        given()
                .queryParam("q", "Manaus,BR")
                .queryParam("appid", "2f7980af9475eb01bb666f8aa5fa62a4")
                .queryParam("unit", "metric")
        .when()
                .get("http://api.openweathermap.org/data/2.5/weather")
        .then()
                .statusCode(200)
                .body("name", is("Manaus"))
        ;
    }

    @Test
    public void naoDeveAcessarSemSenha(){
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
                .statusCode(401)
        ;
    }

    @Test
    public void deveFazerAutenticacaoBasica1(){
        given()
                .log().all()
        .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void deveFazerAutenticacaoBasica2(){
        given()
                .log().all()
                .auth().basic("admin", "senha")
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void deveFazerAutenticacaoBasicaChallenge(){
        given()
                .log().all()
                .auth().preemptive().basic("admin", "senha")
                .when()
                .get("https://restapi.wcaquino.me/basicauth2")
                .then()
                .log().all()
                .statusCode(200)
        ;
    }

}

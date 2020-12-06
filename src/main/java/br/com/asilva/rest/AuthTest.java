package br.com.asilva.rest;

import com.google.gson.annotations.JsonAdapter;
import io.restassured.http.ContentType;
import org.junit.Test;
import sun.swing.StringUIClientPropertyKey;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarWeatherAPIcomChave(){
        given()
                .queryParam("q", "Manaus,BR")
                .queryParam("appid", "cadastrar_chave_na_api")
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

    @Test
    public void deveFazerAutenticacaoComJWT(){

        Map<String,String> login = new HashMap<String, String>();
        login.put("email","anderson@silva");
        login.put("senha", "123456");

        // Logina na API
        // Receber Token
        String token = given()
                .log().all()
                .body(login)
                .contentType(ContentType.JSON)
        .when()
                .post("http://barrigarest.wcaquino.me/signin")
        .then()
            .log().all()
            .statusCode(200)
            .extract().path("token")
        ;

        // Obter Contas
        given()
                .log().all()
                .header("Authorization", "JWT " + token)
        .when()
                .get("http://barrigarest.wcaquino.me/contas")
        .then()
            .log().all()
            .statusCode(200)
            .body("nome", hasItem("Conta Test"))
        ;


    }

}

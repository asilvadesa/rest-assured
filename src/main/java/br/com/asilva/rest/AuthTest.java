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

}

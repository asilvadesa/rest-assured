package br.com.asilva.rest;


import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class OlaMundoTest {

    @Test
    public void testOlaMundo(){
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured(){

        given()
                //Pre condições no given
        .when()
                .get("http://restapi.wcaquino.me/ola")
        .then()
            .statusCode(200);
    }

    @Test
    public void devoConhecerMatchersHamcrest(){
        assertThat("Maria", is("Maria"));
        assertThat(128, is(128));
        assertThat(128, isA(Integer.class));
        assertThat(128d, isA(Double.class));
        assertThat(128d, greaterThan(120d));
        assertThat(128d, lessThan(130d));

        List<Integer> impares = Arrays.asList(1,3,5,7,9);

        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1,3,5,7,9));
        assertThat(impares, containsInAnyOrder(3,1,5,7,9));
        assertThat(impares, hasItem(1));
        assertThat(impares, hasItems(1,3));

    }

}

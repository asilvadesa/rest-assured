package br.com.asilva.rest;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class FileTest {

    @Test
    public void deveObrigarEnvioArquivo(){

        given().log().all()

                .when()
                    .post("https://restapi.wcaquino.me/upload")
                .then()
                    .log().all()
                    .statusCode(404)
                    .body("error", is("Arquivo não enviado"));
        ;
    }

    @Test
    public void deveOFazerUploadArquivo(){

        given().log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf"))

                .when()
                    .post("https://restapi.wcaquino.me/upload")
                .then()
                    .log().all()
                    .statusCode(200)
        ;
    }

    @Test
    public void deveOFazerDownloadArquivo() throws IOException {

        byte[] image = given().log().all()
                .when()
                    .get("https://restapi.wcaquino.me/download")
                .then()
                    .statusCode(200)
                    .extract().asByteArray();

        File imagem = new File("src/main/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();
        Assert.assertThat(imagem.length(), lessThan(100000L));
    }

}

package com.fiap.spring.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiap.spring.Controller.Dto.UsuarioDto;
import infraTest.UtilsTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerSpringITTest {
    @LocalServerPort
    private int porta;

    @BeforeEach
    void setUp() {
        RestAssured.port = porta;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class CriarUsuario {
        @Test
        void deveCriarUsuario() throws JsonProcessingException {
            UsuarioDto usuarioDto = new UsuarioDto("Matheus", "matheus@teste.com", "12999999999");
            given()
                    .contentType(ContentType.JSON)
                    .body(usuarioDto)
                    .when()
                        .post("/usuario")
                    .then()
                        .statusCode(HttpStatus.SC_CREATED)
                    .body(is(UtilsTest.convertJson(usuarioDto)));
        }

        @Test
        void naoDeveCriarUsuarioJaExistente(){
            UsuarioDto usuarioDto = new UsuarioDto("Matheus Rizzo","matheus@gmail.com", "14999998888");
            given()
                    .contentType(ContentType.JSON)
                    .body(usuarioDto)
                    .when()
                        .post("/usuario")
                    .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("Usuário não pode ser cadastrado, pois já existe"));
        }

        @Test
        void naoDeveCriarUsuarioComEnvioIncorreto(){
            UsuarioDto usuarioDtoSemEmail = new UsuarioDto("Matheus", "", "12999999999");
            given()
                    .contentType(ContentType.JSON)
                    .body(usuarioDtoSemEmail)
                    .when()
                        .post("/usuario")
                    .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("E-mail inválido"));

        UsuarioDto usuarioDtoSemNome = new UsuarioDto("", "teste@teste.com", "12999999999");
        given()
                .contentType(ContentType.JSON)
                .body(usuarioDtoSemNome)
                .when()
                    .post("/usuario")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("O nome não pode ser vazio"));
        }
    }

    @Nested
    class AlterarUsuario{
        @Test
        void deveAlterarUsuario() throws JsonProcessingException {
            UsuarioDto usuarioDtoAlterado = new UsuarioDto("Matheus Lot Rizzo","matheus@gmail.com", "14999998888");
            given()
                    .contentType(ContentType.JSON)
                    .body(usuarioDtoAlterado)
                    .when()
                        .put("/usuario")
                    .then()
                        .statusCode(HttpStatus.SC_CREATED)
                    .body(is(UtilsTest.convertJson(usuarioDtoAlterado)));
        }

        @Test
        void naoDeveAlterarUsuarioInexistente(){
            UsuarioDto usuarioDtoInexistente = new UsuarioDto("Matheus Lot Rizzo","inexistente@gmail.com", "14999998888");
            given()
                    .contentType(ContentType.JSON)
                    .body(usuarioDtoInexistente)
                    .when()
                        .put("/usuario")
                    .then()
                    .   statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("Usuário não pode ser alterado, pois nao foi encontrada"));
        }

        @Test
        void naoDeveAlterarUsuarioComEnvioIncorreto(){
            UsuarioDto usuarioDtoSemEmail = new UsuarioDto("Matheus", "", "12999999999");
            given()
                    .contentType(ContentType.JSON)
                    .body(usuarioDtoSemEmail)
                    .when()
                        .put("/usuario")
                    .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("E-mail inválido"));

            UsuarioDto usuarioDtoSemNome = new UsuarioDto("", "teste@teste.com", "12999999999");
            given()
                    .contentType(ContentType.JSON)
                    .body(usuarioDtoSemNome)
                    .when()
                        .put("/usuario")
                    .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("O nome não pode ser vazio"));
        }
    }

    @Nested
    class ExcluirUsuario{
        @Test
        void deveExcluirUsuario(){
            given()
                    .pathParam("email", "matheus2@gmail.com")
                    .when()
                        .delete("/usuario/{email}")
                    .then()
                        .statusCode(HttpStatus.SC_NO_CONTENT);
        }

        @Test
        void naoDeveExcluirUsuarioInexistente(){
            given()
                    .pathParam("email", "naoexiste@gmail.com")
                    .when()
                        .delete("/usuario/{email}")
                    .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("Usuário não pode ser excluido, pois nao foi encontrada"));
        }

        @Test
        void naoDeveExcluirUsuarioComEnvioIncorreto(){
            given()
                    .pathParam("email", "naoexiste")
                    .when()
                        .delete("/usuario/{email}")
                    .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("E-mail inválido"));
        }
    }

    @Nested
    class BuscarUsuario{
        @Test
        void deveBuscarUsuario(){
            given()
                    .pathParam("email", "matheus@gmail.com")
                    .when()
                        .get("/usuario/{email}")
                    .then()
                        .statusCode(HttpStatus.SC_OK);
        }

        @Test
        void naoDeveBuscarUsuarioInexistente(){
            given()
                    .pathParam("email", "naoexiste@gmail.com")
                    .when()
                        .get("/usuario/{email}")
                    .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND);
        }
    }
}
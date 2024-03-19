package com.fiap.spring.Controller;

import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.spring.Controller.Dto.*;
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

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvaliacaoControllerSpringIT {

    @LocalServerPort
    private int porta;

    @BeforeEach
    void setUp() {
        RestAssured.port = porta;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class CriarAvaliacao {

        @Test
        void deveCriarAvaliacao() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoCompleto();

            final AvaliacaoDto avaliacaoDtoRetorno = given()
                    .body(avaliacaoDto)
                    .contentType(ContentType.JSON)
                    .when()
                        .post("/avaliacao")
                    .then()
                        .statusCode(org.apache.http.HttpStatus.SC_CREATED)
                    .extract().as(AvaliacaoDto.class);

            assertNotNull(avaliacaoDtoRetorno.toEntity());
        }
        @Test
        void naoDeveCriarAvaliacaoSemUsuario() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoSemUsuario();

            given()
                    .body(avaliacaoDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/avaliacao")
                    .then()
                    .statusCode(org.apache.http.HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("E-mail inválido"))
            ;
        }
        @Test
        void naoDeveCriarAvaliacaoSemRestaurante() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoSemRestaurante();

            given()
                    .body(avaliacaoDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/avaliacao")
                    .then()
                    .statusCode(org.apache.http.HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("Número de CNPJ inválido"))
            ;
        }
        @Test
        void naoDeveCriarAvaliacaoUsuarioNaoExiste() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoUsuarioNaoExiste();

            given()
                    .body(avaliacaoDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/avaliacao")
                    .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .body("message", is("Usuário não encontrado!"))
            ;
        }
        @Test
        void naoDeveCriarAvaliacaoRestauranteNaoExiste() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoRestauranteNaoExiste();

            given()
                    .body(avaliacaoDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/avaliacao")
                    .then()
                    .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND)
                    .body("message", is("Restaurante não encontrado"))
            ;
        }
        @Test
        void naoDeveCriarAvaliacaoComPontuacaoInvalida() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoComPontuacaoInvalida();

            given()
                    .body(avaliacaoDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/avaliacao")
                    .then()
                    .statusCode(org.apache.http.HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("Valor inválido para a pontuação. É considerado valor válido os valores entre 0 e 5"))
            ;
        }
        @Test
        void naoDeveCriarAvaliacaoSemComentario() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoSemComentario();

            given()
                    .body(avaliacaoDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/avaliacao")
                    .then()
                    .statusCode(org.apache.http.HttpStatus.SC_BAD_REQUEST)
                    .body("message", is("Comentário é obrigatório"))
            ;
        }
    }

    @Nested
    class BuscarAvaliacao{
        @Test
        void deveBuscarTodasAvaliacoesDoRestaurante() throws Exception {
            final String cnpjRestaurante = new CriarObjetosDto().criarRestauranteDto().cnpj();
            given()
                    .pathParam("cnpj", cnpjRestaurante)
                    .when()
                    .get("/avaliacao/{cnpj}")
                    .then()
                    .statusCode(org.apache.http.HttpStatus.SC_OK)
            ;
        }
        @Test
        void naoDeveEncontrarAvaliacoesDoRestaurante() throws Exception {
            final String cnpjRestaurante = new CriarObjetosDto().criarAvaliacaoDtoRestauranteNaoExiste().cnpjRestaurante();
            given()
                    .pathParam("cnpj", cnpjRestaurante)
                    .when()
                    .get("/avaliacao/{cnpj}")
                    .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .body("message", is("Restaurante não encontrado"))
            .log().body()
            ;
        }

    }

    @Nested
    class CriarObjetosDto {

        private AvaliacaoDto criarAvaliacaoDtoCompleto() {
            return new AvaliacaoDto(
                    "teste_avaliacao@fiap.com.br",
                    "94690811000105",
                    5,
                    "sujinho restaurante melhor experiencia em são paulo"
            );
        }

        private AvaliacaoDto criarAvaliacaoDtoSemUsuario() {
            return new AvaliacaoDto(
                    "",
                    "94690811000105",
                    5,
                    "sujinho restaurante melhor experiencia em são paulo"
            );
        }
        private AvaliacaoDto criarAvaliacaoDtoUsuarioNaoExiste() {
            return new AvaliacaoDto(
                    "teste_avaliacao2@fiap.com.br",
                    "94690811000105",
                    5,
                    "sujinho restaurante melhor experiencia em são paulo"
            );
        }
        private AvaliacaoDto criarAvaliacaoDtoSemRestaurante() {
            return new AvaliacaoDto(
                    "teste_avaliacao@fiap.com.br",
                    "",
                    5,
                    "sujinho restaurante melhor experiencia em são paulo"
            );
        }
        private AvaliacaoDto criarAvaliacaoDtoRestauranteNaoExiste() {
            return new AvaliacaoDto(
                    "teste_avaliacao@fiap.com.br",
                    "94690811000100",
                    5,
                    "sujinho restaurante melhor experiencia em são paulo"
            );
        }
        private AvaliacaoDto criarAvaliacaoDtoComPontuacaoInvalida() {
            return new AvaliacaoDto(
                    "teste_avaliacao@fiap.com.br",
                    "94690811000105",
                    6,
                    "sujinho restaurante melhor experiencia em são paulo"
            );
        }
        private AvaliacaoDto criarAvaliacaoDtoSemComentario() {
            return new AvaliacaoDto(
                    "teste_avaliacao@fiap.com.br",
                    "94690811000105",
                    5,
                    null
            );
        }

        private RestauranteDto criarRestauranteDto() {
            return new RestauranteDto(
                    "94690811000105",
                    "Sujinho Restaurante",
                    5,
                    TipoCozinha.JAPONESA,
                    Collections.emptyList(),
                    null);
        }
    }

}

package com.fiap.spring.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.spring.Controller.Dto.EnderecoDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;
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

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestauranteControllerSpringIT {
    @LocalServerPort
    private int porta;

    @BeforeEach
    void setUp() {
        RestAssured.port = porta;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class CriarRestaurante {
        @Test
        void deveCriarRestaurante() throws JsonProcessingException {
            final RestauranteDto restauranteDto = new RestauranteDto(
                "12345678901234",
                "Restaurante Teste",
                10,
                TipoCozinha.ITALIANA,
                new ArrayList<>(),
                new EnderecoDto("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado")
            );

            given()
                .body(restauranteDto)
                .contentType(ContentType.JSON)
                .when()
                    .post("/restaurante")
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .body(is(UtilsTest.convertJson(restauranteDto)));
        }


        @Test
        void naoDeveCriarRestauranteExistente() {
            final RestauranteDto restauranteExistenteDto = new RestauranteDto(
                "95856819000161",
                "Restaurante Bidigaray",
                10,
                TipoCozinha.MEXICANA,
                new ArrayList<>(),
                new EnderecoDto("66816810", "Rua Peru", "273", "apto 301", "Pratinha", "Belém", "PA")
            );

            given()
                .contentType(ContentType.JSON)
                .body(restauranteExistenteDto)
                .when()
                .   post("/restaurante")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Restaurante não pode ser cadastrado, pois já existe"));
        }
    }

    @Nested
    class AlterarRestaurante {
        @Test
        void deveAlterarRestaurante() throws JsonProcessingException {
            final RestauranteDto restauranteAtualizadoDto = new RestauranteDto(
                "95856819000161",
                "Restaurante Atualizado",
                20,
                TipoCozinha.ITALIANA,
                new ArrayList<>(),
                new EnderecoDto("66816810", "Rua Atualizada", "123", "apto 301", "Bairro Atualizado", "Cidade", "Estado")
            );

            given()
                .contentType(ContentType.JSON)
                .body(restauranteAtualizadoDto)
                .when()
                    .put("/restaurante")
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .body(is(UtilsTest.convertJson(restauranteAtualizadoDto)));
        }

        @Test
        void naoDeveAlterarRestauranteInexistente() {
            final RestauranteDto restauranteInexistenteDto = new RestauranteDto(
                "49279377000110",
                "Restaurante Inexistente",
                20,
                TipoCozinha.ITALIANA,
                new ArrayList<>(),
                new EnderecoDto("00000-000", "Rua Inexistente", "123", null, "Bairro Inexistente", "Cidade", "Estado")
            );

            given()
                .contentType(ContentType.JSON)
                .body(restauranteInexistenteDto)
                .when()
                    .put("/restaurante")
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Restaurante não pode ser alterado, pois não foi encontrado"));
        }
    }

    @Nested
    class ExcluirRestaurante {
        @Test
        void deveExcluirRestaurante() {
            final String cnpj = "64292159000100";

            given()
                    .pathParam("cnpj", "64292159000100")
                    .when()
                        .delete("/restaurante/{cnpj}", cnpj)
                    .then()
                        .statusCode(HttpStatus.SC_NO_CONTENT);
        }

        @Test
        void naoDeveExcluirRestauranteInexistente() {
            given()
                    .pathParam("cnpj", "12345678901234")
                    .when()
                        .delete("/restaurante/{cnpj}")
                    .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                    .body("message", is("Restaurante não pode ser excluído, pois não foi encontrado"));
        }
    }
//
//    @Nested
//    class BuscarRestaurante {
//        @Test
//        void deveBuscarRestaurantePorCnpj() throws BusinessException, JsonProcessingException {
//            String cnpj = "12345678901234";
//            RestauranteDto restauranteDto = new RestauranteDto(
//                    cnpj,
//                    "Restaurante Teste",
//                    20,
//                    TipoCozinha.ITALIANA,
//                    new ArrayList<>(),
//                    new EnderecoDto("00000-000", "Rua Teste", "123", null, "Bairro Teste", "Cidade Teste", "Estado Teste")
//            );
//
//            when(restauranteControllerApplication.getBuscarPor(cnpj)).thenReturn(restauranteDto);
//
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                        .get("/restaurante/{cnpj}", cnpj)
//                    .then()
//                        .statusCode(HttpStatus.SC_OK)
//                    .body(is(UtilsTest.convertJson(restauranteDto)));
//
//            verify(restauranteControllerApplication).getBuscarPor(cnpj);
//        }
//
//        @Test
//        void naoDeveEncontrarRestaurantePorCnpjInexistente() throws BusinessException {
//            String cnpjInexistente = "00000000000000";
//            when(restauranteControllerApplication.getBuscarPor(cnpjInexistente))
//                    .thenThrow(new EntidadeNaoEncontrada("Restaurante não encontrado"));
//
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                        .get("/restaurante/{cnpj}", cnpjInexistente)
//                    .then()
//                        .statusCode(HttpStatus.SC_NOT_FOUND)
//                    .body("message", is("Restaurante não encontrado"));
//
//            verify(restauranteControllerApplication).getBuscarPor(cnpjInexistente);
//        }
//
//        @Test
//        void deveBuscarRestaurantePorNome() throws JsonProcessingException, BusinessException {
//            String nome = "Restaurante Teste";
//            RestauranteDto restauranteDto = new RestauranteDto(
//                    "12345678901234",
//                    nome,
//                    20,
//                    TipoCozinha.ITALIANA,
//                    new ArrayList<>(),
//                    new EnderecoDto("00000-000", "Rua Teste", "123", null, "Bairro Teste", "Cidade Teste", "Estado Teste")
//            );
//
//            when(restauranteControllerApplication.getBuscarPorNome(nome)).thenReturn(restauranteDto);
//
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                        .get("/restaurante/nome/{nome}", nome)
//                    .then()
//                        .statusCode(HttpStatus.SC_OK)
//                    .body(is(UtilsTest.convertJson(restauranteDto)));
//
//            verify(restauranteControllerApplication).getBuscarPorNome(nome);
//        }
//
//        @Test
//        void naoDeveEncontrarRestaurantePorNomeInexistente() throws BusinessException {
//            String nomeInexistente = "Nome Inexistente";
//            when(restauranteControllerApplication.getBuscarPorNome(nomeInexistente))
//                    .thenThrow(new EntidadeNaoEncontrada("Restaurante não encontrado para o nome: " + nomeInexistente));
//
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                        .get("/restaurante/nome/{nome}", nomeInexistente)
//                    .then()
//                        .statusCode(HttpStatus.SC_NOT_FOUND)
//                    .body("message", is("Restaurante não encontrado para o nome: " + nomeInexistente));
//
//            verify(restauranteControllerApplication).getBuscarPorNome(nomeInexistente);
//        }
//
//        @Test
//        void deveBuscarRestaurantePorTipoCozinha() throws JsonProcessingException, BusinessException {
//            String tipoCozinha = "ITALIANA";
//            List<RestauranteDto> restaurantes = List.of(
//                    new RestauranteDto(
//                            "12345678901234",
//                            "Restaurante Italiano",
//                            20,
//                            TipoCozinha.ITALIANA,
//                            new ArrayList<>(),
//                            new EnderecoDto("00000-000", "Rua Italiana", "123", null, "Bairro Italiano", "Cidade", "Estado")
//                    )
//            );
//
//            when(restauranteControllerApplication.getBuscarPorTipoCozinha(tipoCozinha)).thenReturn(restaurantes);
//
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                        .get("/restaurante/tipo-cozinha/{tipoCozinha}", tipoCozinha)
//                    .then()
//                        .statusCode(HttpStatus.SC_OK)
//                    .body(is(UtilsTest.convertJson(restaurantes)));
//
//            verify(restauranteControllerApplication).getBuscarPorTipoCozinha(tipoCozinha);
//        }
//
//        @Test
//        void naoDeveEncontrarRestaurantePorTipoCozinhaInexistente() throws BusinessException {
//            String tipoCozinhaInexistente = "ETÍOPE";
//            when(restauranteControllerApplication.getBuscarPorTipoCozinha(tipoCozinhaInexistente))
//                    .thenThrow(new EntidadeNaoEncontrada("Nenhum restaurante encontrado para o tipo de cozinha: " + tipoCozinhaInexistente));
//
//            given()
//                    .contentType(ContentType.JSON)
//                    .when()
//                        .get("/restaurante/tipo-cozinha/{tipoCozinha}", tipoCozinhaInexistente)
//                    .then()
//                        .statusCode(HttpStatus.SC_NOT_FOUND)
//                    .body("message", is("Nenhum restaurante encontrado para o tipo de cozinha: " + tipoCozinhaInexistente));
//
//            verify(restauranteControllerApplication).getBuscarPorTipoCozinha(tipoCozinhaInexistente);
//        }
//    }
}


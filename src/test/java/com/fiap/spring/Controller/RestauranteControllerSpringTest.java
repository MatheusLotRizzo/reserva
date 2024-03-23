package com.fiap.spring.Controller;

import com.fiap.reserva.application.controller.RestauranteControllerApplication;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.spring.Controller.Dto.EnderecoDto;
import com.fiap.spring.Controller.Dto.RestauranteDto;
import com.fiap.spring.Controller.Dto.UsuarioDto;
import com.fiap.spring.conf.DataSourceMock;
import com.fiap.spring.conf.InjecaoDependencia;
import infraTest.UtilsTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(RestauranteControllerSpring.class)
@ExtendWith(SpringExtension.class)
@Import({
    DataSourceMock.class,
    InjecaoDependencia.class,
})
public class RestauranteControllerSpringTest {

    private AutoCloseable autoCloseable;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestauranteRepository restauranteRepository;

    @MockBean
    private RestauranteControllerApplication restauranteControllerApplication;

    @Autowired
    RestauranteControllerSpring restauranteControllerSpring;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteControllerSpring).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    class CriarRestaurante {
        @Test
        void deveCriarRestaurante() throws Exception {
            RestauranteDto restauranteDto = new RestauranteDto(
                    "12345678901234",
                    "Restaurante Teste",
                    10,
                    TipoCozinha.ITALIANA,
                    new ArrayList<>(),
                    new EnderecoDto("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado"));

            when(restauranteControllerApplication.cadastrar(any(RestauranteDto.class))).thenReturn(restauranteDto);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/restaurante")
                            .content(UtilsTest.convertJson(restauranteDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().string(UtilsTest.convertJson(restauranteDto)));

            verify(restauranteControllerApplication).cadastrar(any(RestauranteDto.class));
        }

        @Test
        void naoDeveCriarRestauranteExistente() throws Exception {
            RestauranteDto restauranteExistenteDto  = new RestauranteDto(
                    "12345678901234",
                    "Restaurante Existente",
                    10,
                    TipoCozinha.ITALIANA,
                    new ArrayList<>(),
                    new EnderecoDto("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado"));

            when(restauranteControllerApplication.cadastrar(any(RestauranteDto.class)))
                    .thenThrow(new BusinessException("Restaurante não pode ser cadastrado, pois já existe"));

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/restaurante")
                            .content(UtilsTest.convertJson(restauranteExistenteDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Restaurante não pode ser cadastrado, pois já existe")))
                    );

            verify(restauranteControllerApplication).cadastrar(any(RestauranteDto.class));
        }
    }

    @Nested
    class AlterarRestaurante{
        @Test
        void deveAlterarRestaurante() throws Exception {
            RestauranteDto restauranteDtoNovo = new RestauranteDto(
                    "12345678901234",
                    "Restaurante Atualizado",
                    20,
                    TipoCozinha.ITALIANA,
                    new ArrayList<>(),
                    new EnderecoDto("00000-000", "Rua Atualizada", "123", null, "Bairro", "Cidade", "Estado"));

            when(restauranteControllerApplication.alterar(any(RestauranteDto.class))).thenReturn(restauranteDtoNovo);

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/restaurante")
                            .content(UtilsTest.convertJson(restauranteDtoNovo))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(restauranteDtoNovo))
                    );

            verify(restauranteControllerApplication).alterar(any(RestauranteDto.class));
        }

        @Test
        void naoDeveAlterarRestauranteInexistente() throws Exception {
            RestauranteDto restauranteDtoInexistente = new RestauranteDto(
                    "98765432101234",
                    "Restaurante Inexistente",
                    20,
                    TipoCozinha.ITALIANA,
                    new ArrayList<>(),
                    new EnderecoDto("00000-000", "Rua Inexistente", "123", null, "Bairro", "Cidade", "Estado"));

            when(restauranteControllerApplication.alterar(any(RestauranteDto.class)))
                    .thenThrow(new EntidadeNaoEncontrada("Restaurante não pode ser alterado, pois não foi encontrado"));

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/restaurante")
                            .content(UtilsTest.convertJson(restauranteDtoInexistente))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Restaurante não pode ser alterado, pois não foi encontrado")))
                    );

            verify(restauranteControllerApplication).alterar(any(RestauranteDto.class));
        }
    }

    @Nested
    class ExcluirRestaurante{
        @Test
        void deveExcluirRestaurante() throws Exception {
            String cnpj = "12345678901234";

            // Simulação: O restaurante a ser excluído existe
            doNothing().when(restauranteControllerApplication).excluir(cnpj);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/restaurante/{cnpj}", cnpj)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            verify(restauranteControllerApplication).excluir(cnpj);
        }

        @Test
        void naoDeveExcluirRestauranteInexistente() throws Exception {
            String cnpjInexistente = "12345678901234";

            // Simula o comportamento de lançamento de exceção para o CNPJ inexistente
            doThrow(new EntidadeNaoEncontrada("Restaurante não pode ser excluído, pois não foi encontrado"))
                    .when(restauranteControllerApplication).excluir(cnpjInexistente);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/restaurante/{cnpj}", cnpjInexistente)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Restaurante não pode ser excluído, pois não foi encontrado")))
                    );

            verify(restauranteControllerApplication).excluir(cnpjInexistente);
        }
    }

    @Nested
    class BuscarRestaurante {
        @Test
        void deveBuscarRestaurantePorCnpj() throws Exception {
            String cnpj = "12345678901234";
            RestauranteDto restauranteDto = new RestauranteDto(
                    cnpj,
                    "Restaurante Teste",
                    20,
                    TipoCozinha.ITALIANA,
                    new ArrayList<>(),
                    new EnderecoDto("00000-000", "Rua Teste", "123", null, "Bairro", "Cidade", "Estado"));

            when(restauranteControllerApplication.getBuscarPor(cnpj)).thenReturn(restauranteDto);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/restaurante/{cnpj}", restauranteDto.cnpj())
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(restauranteDto)));

            verify(restauranteControllerApplication).getBuscarPor(cnpj);
        }

        @Test
        void naoDeveEncontrarRestaurantePorCnpjInexistente() throws Exception {
            String cnpjInexistente = "00000000000";
            when(restauranteControllerApplication.getBuscarPor(cnpjInexistente))
                    .thenThrow(new EntidadeNaoEncontrada("Restaurante não encontrado"));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/restaurante/{cnpj}", cnpjInexistente)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Restaurante não encontrado"))
                    ));

            verify(restauranteControllerApplication).getBuscarPor(cnpjInexistente);
        }

        @Test
        void deveBuscarRestaurantePorNome() throws Exception {
            String nome = "Restaurante Teste";
            RestauranteDto restauranteDto = new RestauranteDto(
                    "12345678901234",
                    nome,
                    20,
                    TipoCozinha.ITALIANA,
                    new ArrayList<>(),
                    new EnderecoDto("00000-000", "Rua Teste", "123", null, "Bairro", "Cidade", "Estado"));

            when(restauranteControllerApplication.getBuscarPorNome(nome)).thenReturn(restauranteDto);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/restaurante/nome/{nome}", restauranteDto.nome())
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(restauranteDto)));

            verify(restauranteControllerApplication).getBuscarPorNome(nome);
        }

        @Test
        void naoDeveEncontrarRestaurantePorNomeInexistente() throws Exception {
            String nomeInexistente = "Nome Inexistente";
            when(restauranteControllerApplication.getBuscarPorNome(nomeInexistente))
                    .thenThrow(new EntidadeNaoEncontrada("Restaurante não encontrado para o nome: " + nomeInexistente));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/restaurante/nome/{nome}", nomeInexistente)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Restaurante não encontrado para o nome: " + nomeInexistente))
                    ));

            verify(restauranteControllerApplication).getBuscarPorNome(nomeInexistente);
        }

        @Test
        void deveBuscarRestaurantePorTipoCozinha() throws Exception {
            String tipoCozinha = "ITALIANA";
            List<RestauranteDto> restaurantes = List.of(new RestauranteDto(
                    "12345678901234",
                    "Restaurante Italiano",
                    20,
                    TipoCozinha.ITALIANA,
                    new ArrayList<>(),
                    new EnderecoDto("00000-000", "Rua Italiana", "123", null, "Bairro", "Cidade", "Estado")));

            when(restauranteControllerApplication.getBuscarPorTipoCozinha(tipoCozinha)).thenReturn(restaurantes);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/restaurante/tipo-cozinha/{tipoCozinha}", tipoCozinha)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(restaurantes)));

            verify(restauranteControllerApplication).getBuscarPorTipoCozinha(tipoCozinha);
        }

        @Test
        void naoDeveEncontrarRestaurantePorTipoCozinhaInexistente() throws Exception {
            String tipoCozinhaInexistente = "ETÍOPE";
            when(restauranteControllerApplication.getBuscarPorTipoCozinha(tipoCozinhaInexistente))
                    .thenThrow(new EntidadeNaoEncontrada("Nenhum restaurante encontrado para o tipo de cozinha: " + tipoCozinhaInexistente));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/restaurante/tipo-cozinha/{tipoCozinha}", tipoCozinhaInexistente)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Nenhum restaurante encontrado para o tipo de cozinha: " + tipoCozinhaInexistente))
                    ));

            verify(restauranteControllerApplication).getBuscarPorTipoCozinha(tipoCozinhaInexistente);
        }
    }
}

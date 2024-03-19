package com.fiap.spring.Controller;

import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.application.service.UsuarioService;
import com.fiap.reserva.domain.entity.*;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = AvaliacaoControllerSpring.class)
@ExtendWith(SpringExtension.class)
@Import({
        DataSourceMock.class,
        InjecaoDependencia.class,
})
public class AvaliacaoControllerSpringTest {

    private AutoCloseable autoCloseable;
    private MockMvc mockMvc;

    @MockBean
    private AvaliacaoRepository repository;
    @MockBean
    private RestauranteService restauranteService;
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    private AvaliacaoControllerSpring controller;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    class CriarAvaliacao {

        @Test
        void deveCriarAvaliacao() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoCompleto();
            final RestauranteDto restauranteDto = new CriarObjetosDto().criarRestauranteDto();
            final UsuarioDto usuarioDto = new CriarObjetosDto().criarUsuarioDto();

            when(restauranteService.getBuscarPor(any())).thenReturn(restauranteDto.toEntity());
            when(usuarioService.getBuscarPor(any())).thenReturn(usuarioDto.toEntity());

            when(repository.avaliar(any()))
                    .thenReturn(avaliacaoDto.toEntity());

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/avaliacao")
                    .content(UtilsTest.convertJson(avaliacaoDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    //.andDo(print())
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            verify(repository).avaliar(any());
        }

        @Test
        void naoDeveCriarAvaliacaoSemUsuario() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoSemUsuario();

            when(repository.avaliar(any())).thenReturn(null);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/avaliacao")
                            .content(UtilsTest.convertJson(avaliacaoDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("E-mail inválido")))
                    );
        }

        @Test
        void naoDeveCriarAvaliacaoSemRestaurante() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoSemRestaurante();

            when(repository.avaliar(any())).thenReturn(null);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/avaliacao")
                            .content(UtilsTest.convertJson(avaliacaoDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Número de CNPJ inválido")))
                    );
        }

        @Test
        void naoDeveCriarAvaliacaoUsuarioNaoExiste() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoCompleto();
            final RestauranteDto restauranteDto = new CriarObjetosDto().criarRestauranteDto();
            final UsuarioDto usuarioDto = new CriarObjetosDto().criarUsuarioDto();

            when(restauranteService.getBuscarPor(any())).thenReturn(restauranteDto.toEntity());
            when(usuarioService.getBuscarPor(any())).thenReturn(null);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/avaliacao")
                            .content(UtilsTest.convertJson(avaliacaoDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Usuario é obrigatório")))
                    );

            verify(usuarioService).getBuscarPor(any());
            verify(restauranteService).getBuscarPor(any());
        }

        @Test
        void naoDeveCriarAvaliacaoRestauranteNaoExiste() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoCompleto();
            final UsuarioDto usuarioDto = new CriarObjetosDto().criarUsuarioDto();

            when(restauranteService.getBuscarPor(any())).thenReturn(null);
            when(usuarioService.getBuscarPor(any())).thenReturn(usuarioDto.toEntity());

            when(repository.avaliar(any()))
                    .thenReturn(avaliacaoDto.toEntity());

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/avaliacao")
                            .content(UtilsTest.convertJson(avaliacaoDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Restaurante é obrigatório")))
                    );
            verify(usuarioService).getBuscarPor(any());
            verify(restauranteService).getBuscarPor(any());
        }

        @Test
        void naoDeveCriarAvaliacaoComPontuacaoInvalida() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoComPontuacaoInvalida();

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/avaliacao")
                            .content(UtilsTest.convertJson(avaliacaoDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Valor inválido para a pontuação. É considerado valor válido os valores entre 0 e 5")))
                    );
        }

        @Test
        void naoDeveCriarAvaliacaoSemComentario() throws Exception {
            final AvaliacaoDto avaliacaoDto = new CriarObjetosDto().criarAvaliacaoDtoSemComentario();

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/avaliacao")
                            .content(UtilsTest.convertJson(avaliacaoDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    //.andDo(print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Comentário é obrigatório")))
                    );
        }
    }

    @Nested
    class BuscarAvaliacao{

        @Test
        void deveBuscarTodasAvaliacoesDoRestaurante() throws Exception {
            final String cnpjRestaurante = new CriarObjetosDto().criarRestauranteDto().cnpj();
            final RestauranteDto restauranteDto = new CriarObjetosDto().criarRestauranteDto();

            final List<Avaliacao> avaliacoesConsulta = Arrays.asList(
                    new CriarObjetosDto().criarAvaliacaoDtoCompleto().toEntity(),
                    new CriarObjetosDto().criarAvaliacaoDtoCompleto2().toEntity()
                    );

            final List<AvaliacaoDto> avaliacoesRetorno = Arrays.asList(
                    new CriarObjetosDto().criarAvaliacaoDtoCompleto(),
                    new CriarObjetosDto().criarAvaliacaoDtoCompleto2()
            );

            when(restauranteService.getBuscarPor(any())).thenReturn(restauranteDto.toEntity());

            when(repository.buscarTodasPor(any())).thenReturn(avaliacoesConsulta);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/avaliacao/{cnpj}",cnpjRestaurante)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(UtilsTest.convertJson(avaliacoesRetorno)))
//                    .andDo(print())
            ;

            verify(restauranteService).getBuscarPor(any());
            verify(repository).buscarTodasPor(any());
        }

        @Test
        void naoDeveEncontrarAvaliacoesDoRestaurante() throws Exception {
            final String cnpjRestaurante = new CriarObjetosDto().criarRestauranteDto().cnpj();

            when(restauranteService.getBuscarPor(any())).thenReturn(null);

            when(repository.buscarTodasPor(any())).thenReturn(null);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/avaliacao/{cnpj}",cnpjRestaurante)
                    )
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Restaurante não foi encontrado!")))
                    )
//                    .andDo(print())
            ;

            verify(restauranteService).getBuscarPor(any());
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
        private AvaliacaoDto criarAvaliacaoDtoCompleto2() {
            return new AvaliacaoDto(
                    "teste_avaliacao2@fiap.com.br",
                    "94690811000105",
                    3,
                    "bom"
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
        private AvaliacaoDto criarAvaliacaoDtoSemRestaurante() {
            return new AvaliacaoDto(
                    "teste_avaliacao@fiap.com.br",
                    "",
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
        private UsuarioDto criarUsuarioDto() {
            return new UsuarioDto(
                    "Usuario Teste",
                    "teste_avaliacao@fiap.com.br",
                    "11 99999-8888"
            );
        }
    }

}

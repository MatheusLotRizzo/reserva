package com.fiap.spring.Controller;

import com.fiap.reserva.domain.repository.UsuarioRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = UsuarioControllerSpring.class)
@ExtendWith(SpringExtension.class)
@Import({
        DataSourceMock.class,
        InjecaoDependencia.class,
})
public class UsuarioControllerSpringTest {

    private AutoCloseable autoCloseable;
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioControllerSpring usuarioControllerSpring;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioControllerSpring).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    class CriarUsuario {
        @Test
        void deveCriarUsuario() throws Exception {
            UsuarioDto usuarioDto = new UsuarioDto("Matheus", "matheus@teste.com", "12999999999");
            when(usuarioRepository.buscarPor(any())).thenReturn(null);
            when(usuarioRepository.cadastrar(any())).thenReturn(usuarioDto.toEntity());

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/usuario")
                            .content(UtilsTest.convertJson(usuarioDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().string(UtilsTest.convertJson(usuarioDto)));

            verify(usuarioRepository).buscarPor(any());
            verify(usuarioRepository).cadastrar(any());
        }

        @Test
        void naoDeveCriarUsuarioJaExistente() throws Exception {
            UsuarioDto usuarioDto = new UsuarioDto("Matheus", "matheus@teste.com", "12999999999");
            when(usuarioRepository.buscarPor(any())).thenReturn(usuarioDto.toEntity());

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/usuario")
                            .content(UtilsTest.convertJson(usuarioDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("Usuário não pode ser cadastrado, pois já existe")))
                    );

            verify(usuarioRepository).buscarPor(any());
        }

        @Test
        void naoDeveCriarUsuarioComEnvioIncorreto() throws Exception {
            UsuarioDto usuarioDtoSemEmail = new UsuarioDto("Matheus", "", "");
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/usuario")
                            .content(UtilsTest.convertJson(usuarioDtoSemEmail))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("E-mail inválido")))
                    );

            UsuarioDto usuarioDtoSemNome = new UsuarioDto("", "teste@teste.com", "");
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/usuario")
                            .content(UtilsTest.convertJson(usuarioDtoSemNome))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().json(
                            UtilsTest.convertJson(MessageErrorHandler.create("O nome não pode ser vazio")))
                    );

            verifyNoInteractions(usuarioRepository);
        }
    }
}
package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CadastrarUsuarioTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    CadastrarUsuario cadastrarUsuario;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void naoDeveRetornarUsuarioCadastrandoPassandoNull() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> cadastrarUsuario.executar(null));
        assertEquals("Usuario é obrigatório para realizar o cadastro!", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveRetornarUsuarioCadastrandoUsuario() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.cadastrar(usuario)).thenReturn(usuario);

        final Usuario usuarioEsperado = cadastrarUsuario.executar(usuario);
        assertEquals(usuario, usuarioEsperado);
        verify(repository).cadastrar(usuario);
    }
}
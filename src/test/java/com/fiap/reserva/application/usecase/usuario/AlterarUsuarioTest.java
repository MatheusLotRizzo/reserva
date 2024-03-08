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

public class AlterarUsuarioTest {
    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    AlterarUsuario alterarUsuario;

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
    void naoDeveRetornarUsuarioEnviandoNull() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> alterarUsuario.executar(null));
        assertEquals("Usuario é obrigatorio", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveRetornarUsuarioEnviadoUsuario() throws BusinessException {
        Usuario usuarioAlteracao = new Usuario("Matheus Novo", "teste@teste.com.br");
        when(repository.alterar(usuarioAlteracao)).thenReturn(usuarioAlteracao);

        final Usuario usuarioEsperado = alterarUsuario.executar(usuarioAlteracao);
        assertEquals(usuarioAlteracao, usuarioEsperado);
        assertEquals(usuarioAlteracao.getNome(), usuarioEsperado.getNome());
        verify(repository).alterar(usuarioAlteracao);
        verifyNoMoreInteractions(repository);
    }
}
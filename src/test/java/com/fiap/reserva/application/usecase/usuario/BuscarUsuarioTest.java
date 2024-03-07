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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.CollectionAssert.assertThatCollection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUsuarioTest {
    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private BuscarUsuario buscarUsuario;

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
    void deveRetornarUsuarioBuscandoPorUsuario() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(usuario);

        final Usuario usuarioEsperado = buscarUsuario.getUsuario(usuario);
        assertNotNull(usuarioEsperado);
        assertEquals(usuario, usuarioEsperado);
        verify(repository).buscarPor(usuario);
    }

    @Test
    void naoDeveRetornarUsuarioBuscandoPorNull() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> buscarUsuario.getUsuario(null));
        assertEquals("Usuario é obrigatorio para realizar a busca!", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveRetornarNullBuscandoPorUsuarioInexistente() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(usuario);

        Usuario usuarioInexistente = new Usuario("Matheus2", "teste2@teste.com");

        final Usuario usuarioEsperado = buscarUsuario.getUsuario(usuarioInexistente);
        assertNull(usuarioEsperado);
        verify(repository).buscarPor(usuarioInexistente);
    }

    @Test
    void deveRetornarTodosUsuariosPassandoUsuario() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        Usuario usuario2 = new Usuario("Matheus2", "teste2@teste.com");
        when(repository.buscarTodos(usuario)).thenReturn(Arrays.asList(usuario, usuario2));

        final List<Usuario> esperado = buscarUsuario.getTodos(usuario);
        assertNotNull(esperado);
        assertThatCollection(esperado).hasSize(2);
        assertThatCollection(esperado).filteredOnAssertions(u -> u.equals(usuario));
        verify(repository).buscarTodos(usuario);
    }

    @Test
    void deveRetornarTodosUsuariosPassandoNull() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        Usuario usuario2 = new Usuario("Matheus2", "teste2@teste.com");
        when(repository.buscarTodos(null)).thenReturn(Arrays.asList(usuario, usuario2));

        final List<Usuario> esperado = buscarUsuario.getTodos(null);
        assertNotNull(esperado);
        assertThatCollection(esperado).hasSize(2);
        verify(repository).buscarTodos(null);
    }
}

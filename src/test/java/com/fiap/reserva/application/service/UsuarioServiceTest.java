package com.fiap.reserva.application.service;

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

class UsuarioServiceTest {

    @Mock
    private
    UsuarioRepository repository;

    @InjectMocks
    UsuarioService service;

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
    void naoDeveCadastrarUsuarioExistente() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(usuario);

        final Throwable throwable = assertThrows(BusinessException.class, () -> service.cadastrar(usuario));
        assertEquals("Usuário não pode ser cadastrado, pois já existe", throwable.getMessage());
    }

    @Test
    void deveCadastrarUsuarioInexistente() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(null);
        when(repository.cadastrar(usuario)).thenReturn(usuario);

        final Usuario usuarioCadastrado = service.cadastrar(usuario);
        assertNotNull(usuarioCadastrado);
        assertEquals(usuario, usuarioCadastrado);
        verify(repository).cadastrar(usuario);
    }

    @Test
    void naoDeveAlterarUsuarioInexistente() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> service.alterar(usuario));
        assertEquals("Usuário não pode ser alterado, pois nao foi encontrada", throwable.getMessage());
    }

    @Test
    void deveAlterarUsuarioExistente() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        Usuario usuarioComAlteracao = new Usuario("Matheus 2", "teste@teste.com");
        when(repository.buscarPor(usuarioComAlteracao)).thenReturn(usuario);
        when(repository.alterar(usuarioComAlteracao)).thenReturn(usuarioComAlteracao);

        final Usuario usuarioAlterado = service.alterar(usuarioComAlteracao);
        assertNotNull(usuarioAlterado);
        assertEquals(usuarioComAlteracao, usuarioAlterado);
        verify(repository).alterar(usuarioComAlteracao);
    }

    @Test
    void naoDeveExcluirUsuarioInexistente() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(null);

        final Throwable throwable = assertThrows(BusinessException.class, () -> service.excluir(usuario.getEmail()));
        assertEquals("Usuário não pode ser excluido, pois nao foi encontrada", throwable.getMessage());
    }

    @Test
    void deveExcluirUsuarioExistente() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(usuario);
        service.excluir(usuario.getEmail());
        verify(repository).excluir(usuario.getEmail());
    }

    @Test
    void deveBuscarTodosUsuariosPassandoNull() throws BusinessException {
        Usuario usuario1 = new Usuario("Matheus", "teste@teste.com");
        Usuario usuario2 = new Usuario("Matheus 2", "teste2@teste.com");
        when(repository.buscarTodos(null)).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = service.getTodos(null);
        assertNotNull(usuarios);
        assertThatCollection(usuarios).hasSize(2);
        assertThatCollection(usuarios).filteredOnAssertions(u -> u.equals(usuario1));
        assertThatCollection(usuarios).filteredOnAssertions(u -> u.equals(usuario2));
        verify(repository).buscarTodos(null);
    }

    @Test
    void deveBuscarTodosUsuariosPassandoUsuario() throws BusinessException {
        Usuario usuario1 = new Usuario("Matheus", "teste@teste.com");
        Usuario usuario2 = new Usuario("Matheus 2", "teste2@teste.com");
        when(repository.buscarTodos(usuario1)).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = service.getTodos(usuario1);
        assertNotNull(usuarios);
        assertThatCollection(usuarios).hasSize(2);
        assertThatCollection(usuarios).filteredOnAssertions(u -> u.equals(usuario1));
        assertThatCollection(usuarios).filteredOnAssertions(u -> u.equals(usuario2));
        verify(repository).buscarTodos(usuario1);
    }

    @Test
    void naoDeveBuscarUsuarioPassandoNull() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> service.getBuscarPor(null));
        assertEquals("Usuario é obrigatorio para realizar a busca!", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveBuscarUsuarioPassandoUsuario() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "teste@teste.com");
        when(repository.buscarPor(usuario)).thenReturn(usuario);

        Usuario usuarioEsperado = service.getBuscarPor(usuario);
        assertNotNull(usuarioEsperado);
        assertEquals(usuario, usuarioEsperado);
        verify(repository).buscarPor(usuario);
    }
}
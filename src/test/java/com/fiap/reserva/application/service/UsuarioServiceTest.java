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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks UsuarioService service;

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
    void naoDeveCadastrarUsuarioExistente() {
        Usuario usuario1 = new Usuario("Matheus", "teste@teste.com");
        Usuario usuario2 = new Usuario("Matheus", "teste@teste.com");

        when(repository.buscarPor(usuario2)).thenReturn(usuario1);

        final Throwable throwable = assertThrows(BusinessException.class, () -> service.cadastrar(usuario2));
        assertEquals("Usuário não pode ser cadastrado, pois já existe", throwable.getMessage());
    }
}

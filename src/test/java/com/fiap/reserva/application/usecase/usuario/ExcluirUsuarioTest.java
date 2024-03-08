package com.fiap.reserva.application.usecase.usuario;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ExcluirUsuarioTest {
    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    ExcluirUsuario excluirUsuario;

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
    void naoDeveExcluirUsuarioEnviandoNull() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> excluirUsuario.executar(null));
        assertEquals("Email do usuario é obrigatorio", throwable.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void deveEcluirUsuarioEnviandoEmail() throws BusinessException {
        EmailVo emailVo = new EmailVo("teste@teste.com");
        excluirUsuario.executar(emailVo);
        verify(repository).excluir(emailVo);
        verifyNoMoreInteractions(repository);
    }
}
package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.spring.Controller.Dto.UsuarioDto;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void naoDeveCriarUsuarioComEmailVazio() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario(null));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario(""));
        assertEquals("E-mail inválido", throwable.getMessage());
    }

    @Test
    void naoDeveCriarUsuarioComEmailTendoApenasComCaracteresEspeciais(){
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("_________@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("---------@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());

        throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("@@@@.com.br"));
        assertEquals("E-mail inválido", throwable.getMessage());
    }

    @Test
    void deveCriarUsuarioComEmailValido() {
        Usuario usuario = new Usuario("matheus@gmail.com");
        assertNotNull(usuario);

        usuario = new Usuario("matheus_rizzo@gmail.com.br");
        assertNotNull(usuario);
    }

    @Test
    void naoDeveCriarUsuarioComNomeVazio() {
        Throwable throwable = assertThrows(BusinessException.class, () -> new Usuario("", "matheus@teste.com.br"));
        assertEquals("O nome não pode ser vazio", throwable.getMessage());

        throwable = assertThrows(BusinessException.class, () -> new Usuario(null, "matheus@teste.com.br"));
        assertEquals("O nome não pode ser vazio", throwable.getMessage());
    }

    @Test
    void deveCriarUsuarioComNomeValido() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "matheus@teste.com.br");
        assertNotNull(usuario);
    }

    @Test
    void naoDeveCriarUsuarioComNomeVazioComEmailComCelular(){
        Throwable throwable = assertThrows(BusinessException.class, () -> new Usuario("", "matheus@teste.com", "11999999999"));
        assertEquals("O nome não pode ser vazio", throwable.getMessage());

        throwable = assertThrows(BusinessException.class, () -> new Usuario("", "matheus@teste.com", "11999999999"));
        assertEquals("O nome não pode ser vazio", throwable.getMessage());
    }

    @Test
    void deveCriarUsuarioComNomeValidoComEmailComCelular() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", "matheus@teste.com", "11999999999");
        assertNotNull(usuario);
    }

    @Test
    void naoDeveCriarUsuarioComNomeVazioComEmailVoComCelular(){
        Throwable throwable = assertThrows(BusinessException.class, () -> new Usuario("", new EmailVo("matheus@teste.com"), "11999999999"));
        assertEquals("O nome não pode ser vazio", throwable.getMessage());

        throwable = assertThrows(BusinessException.class, () -> new Usuario("", new EmailVo("matheus@teste.com"), "11999999999"));
        assertEquals("O nome não pode ser vazio", throwable.getMessage());
    }

    @Test
    void deveCriarUsuarioComNomeValidoComEmailVoComCelular() throws BusinessException {
        Usuario usuario = new Usuario("Matheus", new EmailVo("matheus@teste.com"), "11999999999");
        assertNotNull(usuario);
    }

    @Test
    void deveRetornarVerdadeiroCasoDoisUsuariosTenhamMesmoEmail() throws BusinessException{
        Usuario usuario1 = new Usuario("Matheus", "matheus@teste.com");
        Usuario usuario2 = new Usuario("Matheus", "matheus@teste.com");
        assertEquals(true, usuario1.equals(usuario2));
    }

    @Test
    void deveRetornarVerdadeiroEnviandoOMesmoUsuario() throws BusinessException{
        Usuario usuario = new Usuario("Matheus", "matheus@teste.com");
        assertEquals(true, usuario.equals(usuario));
    }

    @Test
    void deveRetornarFalsoCasoDoisUsuariosTenhamEmailDiferentes() throws BusinessException{
        Usuario usuario1 = new Usuario("Matheus", "matheus@teste.com");
        Usuario usuario2 = new Usuario("Matheus 2", "matheus2@teste.com");
        assertEquals(false, usuario1.equals(usuario2));
    }

    @Test
    void deveRetornarFalsoEnviadoObjetoDiferenteDeUsuario() throws BusinessException{
        Usuario usuario = new Usuario("Matheus", "matheus@teste.com");
        EmailVo emailVo = new EmailVo("matheus@teste.com");
        assertEquals(false, usuario.equals(emailVo));
    }

    @Test
    void deveRetornarFalsoEnviadoNull() throws BusinessException{
        Usuario usuario = new Usuario("Matheus", "matheus@teste.com");
        assertEquals(false, usuario.equals(null));
    }

    @Test
    void naoDeveRetornarUsuarioDtoDevidoFaltaDeEmail(){
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> new Usuario("Matheus", "", "11999999999").toDto());
        assertEquals("E-mail inválido", throwable.getMessage());
    }

    @Test
    void deveRetornarUsuarioDto() throws BusinessException{
        UsuarioDto usuarioDto = new Usuario("Matheus", "matheus.rizzo@gmail.com", "11999999999").toDto();
        assertNotNull(usuarioDto);
    }

    @Test
    void deveRetornarEmailStringCorretamente(){
        String email = "matheus@teste.com";
        Usuario usuario = new Usuario(email);
        assertEquals(email, usuario.getEmailString());
    }

    @Test
    void deveRetornarEmailVoCorretamente(){
        EmailVo emailVo = new EmailVo("matheus@teste.com");
        Usuario usuario = new Usuario(emailVo.getEndereco());
        assertEquals(true, emailVo.equals(usuario.getEmail()));
    }

    @Test
    void deveRetornarNomeCorretamente() throws BusinessException{
        String nome = "Matheus";
        Usuario usuario = new Usuario(nome, "matheus@teste.com");
        assertEquals(nome, usuario.getNome());
    }

    @Test
    void deveRetornarCelularCorretamente() throws BusinessException{
        String celular = "11999999999";
        Usuario usuario = new Usuario("Matheus", "matheus.rizzo@gmail.com", celular);
        assertEquals(celular, usuario.getCelular());
    }

    @Test
    void deveRetornarHashCodeCorretamente() throws BusinessException{
        EmailVo emailVo = new EmailVo("matheus@teste.com");
        int hash = Objects.hash(emailVo);
        Usuario usuario = new Usuario("Matheus", emailVo, "11999999999");
        assertEquals(hash, usuario.hashCode());
    }
}
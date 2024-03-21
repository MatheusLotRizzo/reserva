package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    @Test
    void deveCriarRestauranteComTodosOsParametrosString() throws BusinessException {
        String cnpj = "12345678901234";
        String nome = "Restaurante Teste";
        EnderecoVo endereco = new EnderecoVo("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado");
        List<HorarioFuncionamento> horarios = Collections.singletonList(new HorarioFuncionamento(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 14, 8, 0), LocalDateTime.of(2023, 3, 14, 18, 0)));
        int capacidadeMesas = 10;
        TipoCozinha tipoCozinha = TipoCozinha.ITALIANA;

        Restaurante restaurante = new Restaurante(cnpj, nome, endereco, horarios, capacidadeMesas, tipoCozinha);

        assertNotNull(restaurante);
        assertEquals(cnpj, restaurante.getCnpjString());
        assertEquals(nome, restaurante.getNome());
        assertEquals(endereco, restaurante.getEndereco());
        assertEquals(horarios, restaurante.getHorarioFuncionamento());
        assertEquals(capacidadeMesas, restaurante.getCapacidadeMesas());
        assertEquals(tipoCozinha, restaurante.getTipoCozinha());
    }

    @Test
    void deveCriarRestauranteComParametrosMinimos() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        String nome = "Restaurante Simples";

        Restaurante restaurante = new Restaurante(cnpj, nome);

        assertNotNull(restaurante);
        assertEquals(cnpj, restaurante.getCnpj());
        assertEquals(nome, restaurante.getNome());

        assertNull(restaurante.getEndereco());
        assertNull(restaurante.getHorarioFuncionamento());
        assertEquals(0, restaurante.getCapacidadeMesas());
        assertNull(restaurante.getTipoCozinha());
    }


    @Test
    void deveLancarExcecaoParaCnpjInvalido() {
        String cnpjInvalido = "1234";
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> new CnpjVo(cnpjInvalido)
        );

        assertTrue(thrown.getMessage().contains("Número de CNPJ inválido"));
    }

    @Test
    void deveCriarRestauranteComCNPJValido() throws BusinessException {
        String cnpjValido = "12345678901234";
        Restaurante restaurante = new Restaurante(cnpjValido);

        assertEquals(cnpjValido, restaurante.getCnpjString());
    }

    @Test
    void deveRetornarStringCNPJ() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");

        assertEquals("12345678901234", restaurante.getCnpjString());
    }

    @Test
    void deveSerIgualSeCnpjForIgual() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante1 = new Restaurante(cnpj, "Restaurante 1");
        Restaurante restaurante2 = new Restaurante(cnpj, "Restaurante 2");

        assertEquals(restaurante1, restaurante2, "Não foi possível registrar o restaurante, pois já existe um cadastro com este CNPJ.");
    }

    @Test
    void deveRetornarVerdadeiroComparadoASiMesmo() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");

        assertTrue(restaurante.equals(restaurante));
    }

    @Test
    void deveRetornarFalsoQuandoComparadoComNull() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");

        assertFalse(restaurante.equals(null));
    }

    @Test
    void deveRetornarFalsoQuandoComparadoComDiferenteClasse() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");

        assertFalse(restaurante.equals(new Object()));
    }

    @Test
    void deveRetornarHashCodeCorretamente() throws BusinessException {
        CnpjVo cnpjVo = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpjVo, "Restaurante Teste");
        int hash = Objects.hash(cnpjVo);

        assertEquals(hash, restaurante.hashCode());
    }
}

package com.fiap.reserva.application.service;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.spring.Controller.Dto.HorarioFuncionamentoDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestauranteServiceTest {

    @Mock
    private RestauranteRepository repository;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private HorarioSuncionamentoService horarioSuncionamentoService;

    @InjectMocks
    private RestauranteService service;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        service = new RestauranteService(repository, enderecoService, horarioSuncionamentoService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void naoDeveCadastrarRestauranteExistente() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restauranteExistente  = new Restaurante(cnpj, "Restaurante Existente");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(restauranteExistente);

        final Throwable throwable = assertThrows(BusinessException.class, () -> service.cadastrar(restauranteExistente));
        assertEquals("Restaurante não pode ser cadastrado, pois já existe", throwable.getMessage());

        verify(repository).buscarPorCnpj(cnpj);
        verify(repository, never()).cadastrar(restauranteExistente);
    }

    @Test
    void deveCadastrarRestauranteInexistente() throws BusinessException {
        List<HorarioFuncionamento> horariosFuncionamento = List.of(
                new HorarioFuncionamentoDto(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 15, 9, 0), LocalDateTime.of(2023, 3, 15, 13, 0)).toEntity(),
                new HorarioFuncionamentoDto(DayOfWeek.TUESDAY, LocalDateTime.of(2023, 3, 16, 9, 0), LocalDateTime.of(2023, 3, 16, 13, 0)).toEntity()
        );
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo endereco = new EnderecoVo("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado");
        Restaurante novoRestaurante = new Restaurante(cnpj, "Novo Restaurante", endereco, horariosFuncionamento, 10, TipoCozinha.ITALIANA);

        when(repository.buscarPorCnpj(cnpj)).thenReturn(null);
        when(repository.cadastrar(novoRestaurante)).thenReturn(novoRestaurante);
        when(enderecoService.getObter(cnpj, endereco)).thenReturn(null);

        Restaurante restauranteCadastrado = service.cadastrar(novoRestaurante);

        assertNotNull(restauranteCadastrado);
        assertEquals(novoRestaurante, restauranteCadastrado);
        verify(repository).cadastrar(novoRestaurante);
        verify(enderecoService).cadastrar(cnpj, endereco);
    }

    @Test
    void naoDeveAlterarRestauranteInexistente() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("98765432109876");
        Restaurante restauranteInexistente = new Restaurante(cnpj, "Restaurante Inexistente");

        // Simular que o restaurante não existe no banco de dados
        when(repository.buscarPorCnpj(cnpj)).thenReturn(null);

        final Throwable throwable = assertThrows(EntidadeNaoEncontrada.class, () -> service.alterar(restauranteInexistente));
        assertEquals("Restaurante não pode ser alterado, pois não foi encontrado", throwable.getMessage());

        // Verificar se o método buscarPorCnpj foi chamado no repository
        verify(repository).buscarPorCnpj(cnpj);
        // Verificar se o método alterar não foi chamado
        verify(repository, never()).alterar(restauranteInexistente);
    }


    @Test
    void deveAlterarRestauranteExistente() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restauranteExistente = new Restaurante(cnpj, "Restaurante Existente");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(restauranteExistente);
        when(repository.alterar(restauranteExistente)).thenReturn(restauranteExistente);

        Restaurante restauranteAlterado = service.alterar(restauranteExistente);

        assertNotNull(restauranteAlterado);
        assertEquals(restauranteExistente, restauranteAlterado);

        verify(repository).buscarPorCnpj(cnpj);
        verify(repository).alterar(restauranteExistente);
    }



    @Test
    void naoDeveExcluirRestauranteInexistente() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(null);

        final Throwable throwable = assertThrows(EntidadeNaoEncontrada.class, () -> service.excluir(cnpj));
        assertEquals("Restaurante não pode ser excluído, pois não foi encontrado", throwable.getMessage());

        verify(repository).buscarPorCnpj(cnpj);
        verify(repository, never()).excluir(cnpj);
    }

    @Test
    void deveExcluirRestauranteExistente() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restauranteExistente = new Restaurante(cnpj, "Restaurante Existente");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(restauranteExistente);

        service.excluir(cnpj);

        verify(repository).excluir(cnpj);
    }


    @Test
    void deveLancarExcecaoQuandoNomeNaoEncontrado() throws BusinessException {
        String nome = "Nome Inexistente";
        when(repository.buscarPorNome(nome)).thenReturn(null);

        Throwable throwable = assertThrows(EntidadeNaoEncontrada.class, () -> service.getBuscarPorNome(nome));
        assertEquals("Restaurante não encontrado para o nome: " + nome, throwable.getMessage());

        verify(repository).buscarPorNome(nome);
    }

    @Test
    void deveRetornarRestauranteQuandoNomeExistente() throws BusinessException {
        String nome = "Restaurante Teste";
        Restaurante esperado = new Restaurante(new CnpjVo("12345678901234"), nome);
        when(repository.buscarPorNome(nome)).thenReturn(esperado);

        Restaurante resultado = service.getBuscarPorNome(nome);

        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        verify(repository).buscarPorNome(nome);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarRestaurantesPorTipoCozinha() throws BusinessException {
        TipoCozinha tipoCozinha = TipoCozinha.ITALIANA;
        when(repository.buscarPorTipoCozinha(tipoCozinha)).thenReturn(Collections.emptyList());

        final Throwable throwable = assertThrows(EntidadeNaoEncontrada.class, () -> service.getBuscarPorTipoCozinha(tipoCozinha));
        assertEquals("Nenhum restaurante encontrado para o tipo de cozinha: " + tipoCozinha, throwable.getMessage());

        verify(repository).buscarPorTipoCozinha(tipoCozinha);
    }

    @Test
    void deveRetornarRestaurantesPorTipoCozinhaQuandoEncontrados() throws BusinessException {
        TipoCozinha tipoCozinha = TipoCozinha.ITALIANA;
        List<Restaurante> restaurantesEsperados = List.of(
                new Restaurante(new CnpjVo("12345678901234"), "Restaurante Italiano 1"),
                new Restaurante(new CnpjVo("23456789012345"), "Restaurante Italiano 2")
        );

        when(repository.buscarPorTipoCozinha(tipoCozinha)).thenReturn(restaurantesEsperados);

        List<Restaurante> resultado = service.getBuscarPorTipoCozinha(tipoCozinha);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(restaurantesEsperados, resultado);
        verify(repository).buscarPorTipoCozinha(tipoCozinha);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarRestaurantesPorCep() throws BusinessException {
        String cep = "00000000";
        when(repository.buscarPorCep(cep)).thenReturn(List.of());

        final Throwable throwable = assertThrows(BusinessException.class, () -> service.getBuscarPorCep(cep));

        assertEquals("Nenhum restaurante encontrado para o CEP: " + cep, throwable.getMessage());
        verify(repository).buscarPorCep(cep);
    }

    @Test
    void deveRetornarRestaurantesPorCep() throws BusinessException {
        String cep = "12345678";
        List<HorarioFuncionamento> horariosFuncionamento = List.of(
                new HorarioFuncionamentoDto(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 15, 9, 0), LocalDateTime.of(2023, 3, 15, 13, 0)).toEntity(),
                new HorarioFuncionamentoDto(DayOfWeek.TUESDAY, LocalDateTime.of(2023, 3, 16, 9, 0), LocalDateTime.of(2023, 3, 16, 13, 0)).toEntity()
        );
        List<Restaurante> restaurantesEsperados = List.of(
                new Restaurante(new CnpjVo("12345678901234"), "Restaurante A", new EnderecoVo(cep, "", "", "", "", "", ""), horariosFuncionamento, 10, TipoCozinha.BRASILEIRA)
        );
        when(repository.buscarPorCep(cep)).thenReturn(restaurantesEsperados);

        List<Restaurante> resultado = service.getBuscarPorCep(cep);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(restaurantesEsperados, resultado);
        verify(repository).buscarPorCep(cep);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoPorCnpj() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(null);

        final Throwable throwable = assertThrows(EntidadeNaoEncontrada.class, () -> service.getBuscarPor(cnpj));

        assertEquals("Restaurante não encontrado", throwable.getMessage());

        verify(repository).buscarPorCnpj(cnpj);
    }

    @Test
    void deveRetornarRestauranteQuandoEncontradoPorCnpj() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante esperado = new Restaurante(cnpj, "Restaurante Teste");

        when(repository.buscarPorCnpj(cnpj)).thenReturn(esperado);

        Restaurante resultado = service.getBuscarPor(cnpj);

        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        verify(repository).buscarPorCnpj(cnpj);
    }

    @Test
    void deveLancarExcecaoSeRestauranteForNuloAoObterLotacaoMaxima() {
        final Throwable throwable = assertThrows(BusinessException.class, () -> service.obterLocacaoMaxRestaurante(null));

        assertEquals("Restaurante é obrigatório para obter a lotação máxima.", throwable.getMessage());
    }

    @Test
    void deveRetornarLotacaoMaximaDoRestaurante() throws BusinessException {
        CnpjVo cnpj = new CnpjVo("12345678901234");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste");
        int lotacaoMaximaEsperada = 100;

        when(repository.obterLotacaoMaximaRestaurante(restaurante)).thenReturn(lotacaoMaximaEsperada);

        int lotacaoMaximaObtida = service.obterLocacaoMaxRestaurante(restaurante);

        assertNotNull(lotacaoMaximaObtida);
        assertEquals(lotacaoMaximaEsperada, lotacaoMaximaObtida);
        verify(repository).obterLotacaoMaximaRestaurante(restaurante);
    }

    @Test
    void deveCadastrarEnderecoQuandoNaoExistente() throws BusinessException {
        List<HorarioFuncionamento> horariosFuncionamento = List.of(
                new HorarioFuncionamentoDto(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 15, 9, 0), LocalDateTime.of(2023, 3, 15, 13, 0)).toEntity(),
                new HorarioFuncionamentoDto(DayOfWeek.TUESDAY, LocalDateTime.of(2023, 3, 16, 9, 0), LocalDateTime.of(2023, 3, 16, 13, 0)).toEntity()
        );
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo novoEndereco = new EnderecoVo("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste", novoEndereco, horariosFuncionamento, 10, TipoCozinha.ITALIANA);

        // Simula que o endereço não existe
        when(enderecoService.getObter(cnpj, novoEndereco)).thenReturn(null);

        // Método que chama cadastrarOuAlterarEndereco internamente
        service.cadastrar(restaurante);

        // Verifica se o método cadastrar é chamado no serviço de endereço
        verify(enderecoService).cadastrar(cnpj, novoEndereco);
    }


    @Test
    void deveAlterarEnderecoQuandoExistente() throws BusinessException {
        List<HorarioFuncionamento> horariosFuncionamento = List.of(
                new HorarioFuncionamentoDto(DayOfWeek.MONDAY, LocalDateTime.of(2023, 3, 15, 9, 0), LocalDateTime.of(2023, 3, 15, 13, 0)).toEntity(),
                new HorarioFuncionamentoDto(DayOfWeek.TUESDAY, LocalDateTime.of(2023, 3, 16, 9, 0), LocalDateTime.of(2023, 3, 16, 13, 0)).toEntity()
        );
        CnpjVo cnpj = new CnpjVo("12345678901234");
        EnderecoVo enderecoExistente = new EnderecoVo("00000-000", "Rua Exemplo", "123", null, "Bairro", "Cidade", "Estado");
        Restaurante restaurante = new Restaurante(cnpj, "Restaurante Teste", enderecoExistente, horariosFuncionamento, 10, TipoCozinha.ITALIANA);

        when(enderecoService.getObter(cnpj, enderecoExistente)).thenReturn(enderecoExistente);

        service.cadastrar(restaurante);

        verify(enderecoService).alterar(cnpj, enderecoExistente);
    }

}

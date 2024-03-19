package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.*;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.exception.EntidadeNaoEncontrada;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.util.List;

public class RestauranteService {
    private final RestauranteRepository repository;
    private final EnderecoService enderecoService;
    private final HorarioSuncionamentoService horarioSuncionamentoService;

    public RestauranteService(
            RestauranteRepository repository,
            EnderecoService enderecoService,
            HorarioSuncionamentoService horarioSuncionamentoService) {
        this.repository = repository;
        this.enderecoService = enderecoService;
        this.horarioSuncionamentoService = horarioSuncionamentoService;
    }

    public Restaurante cadastrar(final Restaurante restaurante) throws BusinessException {
        Restaurante existente = repository.buscarPorCnpj(restaurante.getCnpj());
        if (existente != null) {
            throw new BusinessException("Restaurante não pode ser cadastrado, pois já existe");
        }
        Restaurante restauranteRetorno = repository.cadastrar(restaurante);
        cadastrarOuAlterarEndereco(restaurante);
        cadastrarOuAlterarHorarioFuncionamento(restaurante);
        return restauranteRetorno;
    }

    public Restaurante alterar(final Restaurante restaurante) throws BusinessException {
        cadastrarOuAlterarEndereco(restaurante);
        cadastrarOuAlterarHorarioFuncionamento(restaurante);
        AlterarRestaurante alterarRestaurante = new AlterarRestaurante(repository);
        return alterarRestaurante.executar(restaurante);
    }

    public void excluir(final CnpjVo cnpj) throws BusinessException {
        Restaurante restaurante = repository.buscarPorCnpj(cnpj);
        if (restaurante == null) {
            throw new EntidadeNaoEncontrada("Restaurante não pode ser excluído, pois não foi encontrado");
        }

        repository.excluir(cnpj);
    }

    public Restaurante getBuscarPorNome(final String nome) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorNome(nome);
    }

    public List<Restaurante> getBuscarPorTipoCozinha(final TipoCozinha tipoCozinha) throws BusinessException {
        List<Restaurante> restaurantes = new BuscarRestaurante(repository).getRestaurantePorTipoCozinha(tipoCozinha);
        if (restaurantes == null || restaurantes.isEmpty()) {
            throw new EntidadeNaoEncontrada("Nenhum restaurante encontrado para o tipo de cozinha: " + tipoCozinha);
        }
        return restaurantes;
    }

    public List<Restaurante> getBuscarPorLocalizacao(final EnderecoVo enderecoVo) throws BusinessException {
        List<Restaurante> restaurantes = new BuscarRestaurante(repository).getRestaurantePorLocalizacao(enderecoVo);
        if (restaurantes == null || restaurantes.isEmpty()) {
            throw new EntidadeNaoEncontrada("Nenhum restaurante encontrado para a localização especificada.");
        }
        return restaurantes;
    }

    public Restaurante getBuscarPor(final CnpjVo cnpj) throws BusinessException {
        Restaurante restaurante = repository.buscarPorCnpj(cnpj);
        if (restaurante == null) {
            throw new EntidadeNaoEncontrada("Restaurante não encontrado");
        }
        return restaurante;
    }

    public Integer obterLocacaoMaxRestaurante(Restaurante restaurante) throws BusinessException {
        if (restaurante == null) {
            throw new BusinessException("Restaurante é obrigatório para obter a lotação máxima.");
        }

        return repository.obterLotacaoMaximaRestaurante(restaurante);
    }

    private void cadastrarOuAlterarEndereco(final Restaurante restaurante) throws BusinessException{
        if (restaurante.getEndereco() != null) {
            EnderecoVo enderecoVo = enderecoService.getObter(restaurante.getCnpj(),restaurante.getEndereco());
            if(enderecoVo != null) {
                enderecoService.alterar(restaurante.getCnpj(), restaurante.getEndereco());
            } else {
                enderecoService.cadastrar(restaurante.getCnpj(), restaurante.getEndereco());
            }
        }
    }

    private void cadastrarOuAlterarHorarioFuncionamento(final Restaurante restaurante) throws BusinessException {
        if (restaurante.getHorarioFuncionamento() != null && !restaurante.getHorarioFuncionamento().isEmpty()) {
            for (HorarioFuncionamento horario : restaurante.getHorarioFuncionamento()) {
                HorarioFuncionamento horarioExistente = horarioSuncionamentoService.getObter(restaurante.getCnpj(), horario);
                if (horarioExistente != null) {
                    horarioSuncionamentoService.alterar(restaurante.getCnpj(), horario);
                } else {
                    horarioSuncionamentoService.cadastrar(restaurante.getCnpj(), horario);
                }
            }
        }
    }
}
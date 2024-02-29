package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.*;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;

import java.util.List;

public class RestauranteService {
    private RestauranteRepository repository;
    private EnderecoService enderecoService;
    private HorarioSuncionamentoService horarioSuncionamentoService;

    public RestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public Restaurante cadastrar(final Restaurante restaurante) throws BusinessException {
        Restaurante restauranteRetorno = new CadastrarRestaurante(repository).executar(restaurante);
        enderecoService.cadastrar(restaurante.getCnpj(),restaurante.getEndereco());
        horarioSuncionamentoService.cadastrar(restaurante.getCnpj(),restaurante.getHorarioFuncionamento());
        return restauranteRetorno;
    }

    public Restaurante alterar(final Restaurante restaurante) throws BusinessException {

        if (restaurante.getEndereco() != null) {
            enderecoService.alterar(restaurante.getCnpj(), restaurante.getEndereco());
        }
        if (restaurante.getHorarioFuncionamento() != null) {
            horarioSuncionamentoService.alterar(restaurante.getCnpj(), restaurante.getHorarioFuncionamento());
        }
        return new AlterarRestaurante(repository).executar(restaurante);
    }

    public void excluir(final CnpjVo cnpj){
        new ExcluirRestaurante(repository).executar(cnpj);
    }

    public Restaurante getBuscarPorNome(final String nome) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorNome(nome);
    }

    public List<Restaurante> getBuscarPorTipoCozinha(final TipoCozinha tipoCozinha) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorTipoCozinha(tipoCozinha);
    }

    public Restaurante getBuscarPor(final CnpjVo cnpj) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePor(cnpj);
    }

    public Integer obterLocacaoMaxRestaurante(Restaurante restaurante) throws BusinessException{
        return new ObterLotacaoMaximaRestaurante(repository).executar(restaurante);
    }
}

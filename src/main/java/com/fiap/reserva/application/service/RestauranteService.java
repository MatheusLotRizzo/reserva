package com.fiap.reserva.application.service;

import com.fiap.reserva.application.usecase.restaurante.*;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
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
        Restaurante restauranteRetorno = new CadastrarRestaurante(repository).executar(restaurante);
        cadastrarOuAlterarEndereco(restaurante);
        cadastrarOuAlterarHorarioFuncionamento(restaurante);
        return restauranteRetorno;
    }

    public Restaurante alterar(final Restaurante restaurante) throws BusinessException {
        cadastrarOuAlterarEndereco(restaurante);
        cadastrarOuAlterarHorarioFuncionamento(restaurante);
        return new AlterarRestaurante(repository).executar(restaurante);
    }

	public void excluir(final CnpjVo cnpj) throws BusinessException{
        new ExcluirRestaurante(repository).executar(cnpj);
    }

    public Restaurante getBuscarPorNome(final String nome) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorNome(nome);
    }

    public List<Restaurante> getBuscarPorTipoCozinha(final TipoCozinha tipoCozinha) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorTipoCozinha(tipoCozinha);
    }

    public List<Restaurante> getBuscarPorLocalizacao(final EnderecoVo enderecoVo) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorLocalizacao(enderecoVo);
    }

    public Restaurante getBuscarPor(final CnpjVo cnpj) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePor(cnpj);
    }

    public Integer obterLocacaoMaxRestaurante(Restaurante restaurante) throws BusinessException{
        return new ObterLotacaoMaximaRestaurante(repository).executar(restaurante);
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

    private void cadastrarOuAlterarHorarioFuncionamento(final Restaurante restaurante) throws BusinessException{
        if (restaurante.getHorarioFuncionamento() != null) {
            HorarioFuncionamento horarioFuncionamento = horarioSuncionamentoService.getObter(restaurante.getCnpj(),restaurante.getHorarioFuncionamento());
            if(horarioFuncionamento != null) {
                horarioSuncionamentoService.alterar(restaurante.getCnpj(), restaurante.getHorarioFuncionamento());
            } else {
                horarioSuncionamentoService.cadastrar(restaurante.getCnpj(), restaurante.getHorarioFuncionamento());
            }
        }
    }
}

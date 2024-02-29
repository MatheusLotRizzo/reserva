package com.fiap.reserva.application.controller;

import com.fiap.reserva.application.usecase.restaurante.AlterarRestaurante;
import com.fiap.reserva.application.usecase.restaurante.BuscarRestaurante;
import com.fiap.reserva.application.usecase.restaurante.CadastrarRestaurante;
import com.fiap.reserva.application.usecase.restaurante.ExcluirRestaurante;
import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;

import java.time.LocalDateTime;

public class RestauranteController {
    private final RestauranteRepository repository;

    public RestauranteController(RestauranteRepository restauranteRepository) {
        this.repository = restauranteRepository;
    }

    public void cadastrar(final String cnpj,
                          final String nome,
                          final int capacidade,
                          final String tipoCozinha,
                          final String horarioAbertura,
                          final String horarioEncerramento,
                          final String cep,
                          final String logradouro,
                          final String numero,
                          final String complemento,
                          final String bairro,
                          final String cidade,
                          final String estado){
        final Restaurante restaurante = construirRestaurante(cnpj,
                nome,
                capacidade,
                tipoCozinha,
                horarioAbertura,
                horarioEncerramento,
                cep,
                logradouro,
                numero,
                complemento,
                bairro,
                cidade,
                estado);

        new CadastrarRestaurante(repository).executar(restaurante);
    }

    public void alterar(final String cnpj,
                        final String nome,
                        final int capacidade,
                        final String tipoCozinha,
                        final String horarioAbertura,
                        final String horarioEncerramento,
                        final String cep,
                        final String logradouro,
                        final String numero,
                        final String complemento,
                        final String bairro,
                        final String cidade,
                        final String estado
                        ) throws BusinessException {
        final Restaurante restaurante = construirRestaurante(cnpj,
                                                    nome,
                                                    capacidade,
                                                    tipoCozinha,
                                                    horarioAbertura,
                                                    horarioEncerramento,
                                                    cep,
                                                    logradouro,
                                                    numero,
                                                    complemento,
                                                    bairro,
                                                    cidade,
                                                    estado);

        new AlterarRestaurante(repository).executar(restaurante);
    }

    public void excluir(final String cnpj){
        new ExcluirRestaurante(repository).executar(cnpj);
    }

    public Restaurante getBuscarPor(final String cnpj) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePor(new CnpjVo(cnpj));
    }

    public Restaurante getBuscarPorNome(final String nome) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorNome(nome);
    }

    public Restaurante getBuscarPorTipoCozinha(final String tipoCozinha) throws BusinessException{
        return new BuscarRestaurante(repository).getRestaurantePorTipoCozinha(TipoCozinha.valueOf(tipoCozinha));
    }

    private Restaurante construirRestaurante(
            final String cnpj,
             final String nome,
             final int capacidade,
             final String tipoCozinha,
             final String horarioAbertura,
             final String horarioEncerramento,
             final String cep,
             final String logradouro,
             final String numero,
             final String complemento,
             final String bairro,
             final String cidade,
             final String estado) {
        EnderecoVo enderecoVo = new EnderecoVo(cep, logradouro, numero, complemento, bairro, cidade, estado);
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento(LocalDateTime.parse(horarioAbertura),LocalDateTime.parse(horarioEncerramento));

        return new Restaurante(new CnpjVo(cnpj),
                nome,
                enderecoVo,
                horarioFuncionamento,
                capacidade,
                TipoCozinha.valueOf(tipoCozinha));
    }
}

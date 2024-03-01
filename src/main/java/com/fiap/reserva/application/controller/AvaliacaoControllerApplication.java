package com.fiap.reserva.application.controller;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.reserva.application.service.AvaliacaoService;
import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.application.service.UsuarioService;
import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;

public class AvaliacaoControllerApplication {

    private AvaliacaoService service;
    private RestauranteService restauranteService;
    private UsuarioService usuarioService;

    public AvaliacaoDto avaliar(final AvaliacaoDto avaliacaoDto) throws BusinessException{
        return construirAvaliacaoDto(service.avaliar(construirAvaliacao(avaliacaoDto)));
    }

    public List<AvaliacaoDto> getBuscarTodos(final String cnpj) throws BusinessException{
        List<Avaliacao> avaliacoes = service.getBuscarTodos(new CnpjVo(cnpj));

        return avaliacoes.stream()
                        .map(this::construirAvaliacaoDto)
                        .collect(Collectors.toList());
    }

    private Avaliacao construirAvaliacao(final AvaliacaoDto avaliacaoDto) {
        try {
            final Usuario usuario = usuarioService.getBuscarPorEmail(new EmailVo(avaliacaoDto.emailUsuario()));
            final Restaurante restaurante = restauranteService.getBuscarPor(new CnpjVo(avaliacaoDto.cnpjRestaurante()));
            return new Avaliacao(usuario,restaurante,avaliacaoDto.pontuacao(),avaliacaoDto.comentario());
        } catch (BusinessException e) {
            System.err.println("Erro de negócios: " + e.getMessage());

            return null;
        }
    }

    private AvaliacaoDto construirAvaliacaoDto(final Avaliacao avaliacao){
        return avaliacao.toDto();
    }
}

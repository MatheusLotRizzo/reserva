package com.fiap.reserva.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.fiap.reserva.application.service.AvaliacaoService;
import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;

public class AvaliacaoControllerApplication {
    private AvaliacaoService service;

    public AvaliacaoDto avaliar(final AvaliacaoDto avaliacaoDto) throws BusinessException{
        return toAvaliacaoDto(service.avaliar(avaliacaoDto.toEntity()));
    }

    public List<AvaliacaoDto> getBuscarTodasRerservasRestaurantePeloCNPJ(final String cnpj) throws BusinessException{
        List<Avaliacao> avaliacoes = service.getBuscarTodasRerservasRestaurantePeloCNPJ(new CnpjVo(cnpj));

        return avaliacoes.stream()
                        .map(this::toAvaliacaoDto)
                        .collect(Collectors.toList());
    }

      public AvaliacaoDto toAvaliacaoDto(final Avaliacao avaliacaoEntity){
        return new AvaliacaoDto(
                avaliacaoEntity.getUsuario().getEmailString(),
                avaliacaoEntity.getRestaurante().getCnpjString(),
                avaliacaoEntity.getPontuacao(),
                avaliacaoEntity.getComentario()
        );
    }
}

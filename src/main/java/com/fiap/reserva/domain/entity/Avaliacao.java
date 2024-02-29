package com.fiap.reserva.domain.entity;

import com.fiap.spring.Controller.Dto.AvaliacaoDto;

public class Avaliacao {
    private Usuario usuario;
    private Restaurante restaurante;
    private int pontuacao;

    private String comentario;

    public Avaliacao(Usuario usuario, Restaurante restaurante, int pontuacao, String comentario) {
        this.usuario = usuario;
        this.restaurante = restaurante;
        this.pontuacao = pontuacao;
        this.comentario = comentario;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getComentario() {
        return comentario;
    }

    public AvaliacaoDto toDto(){
        return new AvaliacaoDto(
                usuario.getEmailString(),
                restaurante.getCnpjString(),
                pontuacao,
                comentario
        );
    }
}

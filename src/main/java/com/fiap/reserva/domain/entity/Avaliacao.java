package com.fiap.reserva.domain.entity;

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
}

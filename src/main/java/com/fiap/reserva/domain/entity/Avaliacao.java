package com.fiap.reserva.domain.entity;

import com.fiap.reserva.domain.exception.BusinessException;

public class Avaliacao {
    private Usuario usuario;
    private Restaurante restaurante;
    private int pontuacao;

    private String comentario;

    public Avaliacao(Usuario usuario, Restaurante restaurante, int pontuacao, String comentario) throws BusinessException {

        if (usuario == null){
            throw new BusinessException("Usuario válido é obrigatório");
        }

        if (restaurante == null ){
            throw new BusinessException("Restaurante válido é obrigatório");
        }

        if (comentario == null || comentario.isEmpty()){
            throw new BusinessException("Comentário é obrigatório");
        }

        if (!(pontuacao >= 0 && pontuacao <= 5)){
            throw new BusinessException("Valor inválido para a pontuação. É considerado valor válido os valores entre 0 e 5");
        }

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

}

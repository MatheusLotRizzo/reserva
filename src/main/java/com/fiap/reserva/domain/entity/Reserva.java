package com.fiap.reserva.domain.entity;

import java.time.LocalDateTime;

public class Reserva {
    private Usuario usuario;
    private Restaurante restaurante;
    private LocalDateTime dataHora;
    private int quantidadeLugares;
    private StatusReserva status = StatusReserva.DISPONIVEL;

    public Reserva(Usuario usuario, Restaurante restaurante, LocalDateTime dataHora, int quantidadeLugares) {
        if (usuario == null){
            throw new IllegalArgumentException("Usuario obrigatorio");
        }

        if (restaurante == null ){
            throw new IllegalArgumentException("Restaurante obrigatorio");
        }

        if (dataHora.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Data e hora da reserva deve ser superior a data e hora atual");
        }

        if ( quantidadeLugares < 1){
            throw new IllegalArgumentException("É necessário ao menos 1 lugar para seguir com a reserva");
        }

        this.usuario = usuario;
        this.restaurante = restaurante;
        this.dataHora = dataHora;
        this.quantidadeLugares = quantidadeLugares;
    }

    public Reserva(Usuario usuario, Restaurante restaurante, LocalDateTime dataHora, int quantidadeLugares, StatusReserva status) {
        this(usuario, restaurante, dataHora, quantidadeLugares);
        this.status = status;
    }


    public void reservar(){
        if (this.status == StatusReserva.CANCELADO) {
            throw new IllegalArgumentException("Esta reserva ja esta cancelada");
        }
        this.status = StatusReserva.RESERVADO;
    }

    public void cancelar(){
        if (this.status == StatusReserva.RESERVADO) {
            this.status = StatusReserva.RESERVADO;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getQuantidadeLugares() {
        return quantidadeLugares;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public String getStatusString() {
        return status.toString();
    }
}

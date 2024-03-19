package com.fiap.reserva.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.fiap.reserva.domain.exception.BusinessException;

public class Reserva {
    private UUID numeroReserva;
    private Usuario usuario;
    private Restaurante restaurante;
    private LocalDateTime dataHora;
    private SituacaoReserva situacao;

    public Reserva(UUID numeroReserva, Usuario usuario, Restaurante restaurante, LocalDateTime dataHora, SituacaoReserva situacao) throws BusinessException {
        if (numeroReserva == null){
            throw new BusinessException("Número da reserva obrigatório");
        }

        if (usuario == null){
            throw new BusinessException("Usuario obrigatório");
        }

        if (restaurante == null ){
            throw new BusinessException("Restaurante obrigatório");
        }

        if (dataHora == null){
            throw new BusinessException("Data e hora obrigatório");
        }

        if(situacao == null){
            throw new BusinessException("Situação da reserva é obrigatória");
        }

        this.numeroReserva = numeroReserva;
        this.usuario = usuario;
        this.restaurante = restaurante;
        this.dataHora = dataHora;
        this.situacao = situacao;
    }

    public void reservar() throws BusinessException{
        validarReservaEstaCancelada();
        validarReservaJaReservada();
        validarReservaEstaConcluida();
        this.situacao = SituacaoReserva.RESERVADO;
    }
    
    public void cancelar() throws BusinessException{
        validarReservaEstaCancelada();
        validarReservaEstaDisponivel();
        validarReservaEstaConcluida();
        
        this.situacao = SituacaoReserva.CANCELADO;
    }
    
    public void concluir() throws BusinessException{
        validarReservaEstaCancelada();
        validarReservaEstaDisponivel();
        validarReservaEstaConcluida();
        
        this.situacao = SituacaoReserva.CONCLUIDO;
    }

    private void validarReservaJaReservada() throws BusinessException {
        if(this.situacao == SituacaoReserva.RESERVADO){
            throw new BusinessException("Esta reserva ja esta reservada");
        }
    }

    private void validarReservaEstaDisponivel() throws BusinessException {
        if (this.situacao == SituacaoReserva.DISPONIVEL) {
            throw new BusinessException("Não é possivel cancelar reserva uma disponivel");
        }
    }

    private void validarReservaEstaCancelada() throws BusinessException {
        if (this.situacao == SituacaoReserva.CANCELADO) {
            throw new BusinessException("Esta reserva ja esta cancelada");
        }
    }
    
    private void validarReservaEstaConcluida() throws BusinessException {
        if (this.situacao == SituacaoReserva.CONCLUIDO) {
            throw new BusinessException("Esta reserva ja esta concluida");
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

    public SituacaoReserva getSituacao() {
        return situacao;
    }

    public UUID getNumeroReserva() {
        return numeroReserva;
    }

	@Override
	public int hashCode() {
		return Objects.hash(numeroReserva);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			return this.hashCode() == obj.hashCode();
		}
		return false;
	}
}

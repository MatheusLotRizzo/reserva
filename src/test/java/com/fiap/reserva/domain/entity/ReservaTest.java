package com.fiap.reserva.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fiap.reserva.domain.exception.BusinessException;

class ReservaTest {

    @Test
    void naoDeveCriarReservaCasoNumeroDaReservaNaoAtribuido(){
        final Throwable throwable = assertThrows(BusinessException.class, () -> 
        new Reserva(null, null, null, null, null));
        
        assertEquals("Número da reserva obrigatório", throwable.getMessage());
    }

    @Test
    void naoDeveCriarReservaCasoUsuarioNaoAtribuido(){
        final Throwable throwable = assertThrows(BusinessException.class, () -> 
        new Reserva(UUID.randomUUID(), null, null, null, null));
        
        assertEquals("Usuario obrigatório", throwable.getMessage());
    }

    @Test
    void naoDeveCriarReservaCasoRestauranteNaoAtribuido(){
        final Throwable throwable = assertThrows(BusinessException.class, () -> 
        new Reserva(
            UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            null, null, null));
        
        assertEquals("Restaurante obrigatório", throwable.getMessage());
    }

    @Test
    void naoDeveCriarReservaCasoDataHoraReservaNaoAtribuida(){
        final Throwable throwable = assertThrows(BusinessException.class, () -> new Reserva(
            UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            null, null));
        
        assertEquals("Data e hora obrigatório", throwable.getMessage());
    }

    @Test
    void naoDeveCriarReservaCasoSituacaoReservaNaoAtribuida(){
        final Throwable throwable = assertThrows(BusinessException.class, () -> new Reserva(
            UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            LocalDateTime.now(), 
            null));
        
        assertEquals("Situação da reserva é obrigatória", throwable.getMessage());
    }

    @Test
    void deveCriarReservaCasoTodosDadosObrigatoriosTenhamSidoEnviados() throws BusinessException{
        final Reserva reserva = new Reserva(
            UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            LocalDateTime.now(), 
            SituacaoReserva.DISPONIVEL
        );

        assertNotNull(reserva);
    }

    @Test
    void deveReservarUmaReservaCasoSituacaoDisponivel() throws BusinessException{
        final Reserva reserva = new Reserva(
            UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            LocalDateTime.now(), 
            SituacaoReserva.DISPONIVEL
        );
        reserva.reservar();
        assertNotNull(reserva);
        assertNotNull(reserva.getNumeroReserva());
        assertNotNull(reserva.getDataHora());
        assertNotNull(reserva.getRestaurante());
        assertNotNull(reserva.getUsuario());
        assertEquals(reserva.getSituacao(), SituacaoReserva.RESERVADO);
    }

    @Test
    void naoDeveReservarUmaReservaCasoSituacaoCancelada() throws BusinessException{
        final Throwable throwable = assertThrows(BusinessException.class, () ->{
            final Reserva reserva = new Reserva(
                UUID.randomUUID(), 
                new Usuario("fualno@teste.com"), 
                new Restaurante("12345678900000"), 
                LocalDateTime.now(), 
                SituacaoReserva.CANCELADO
            );
            reserva.reservar();
        });

        assertEquals("Esta reserva ja esta cancelada", throwable.getMessage());
    }

    @Test
    void naoDeveReservarUmaReservaCasoSituacaoReservada() throws BusinessException{
        final Throwable throwable = assertThrows(BusinessException.class, () ->{
            final Reserva reserva = new Reserva(
                UUID.randomUUID(), 
                new Usuario("fualno@teste.com"), 
                new Restaurante("12345678900000"), 
                LocalDateTime.now(), 
                SituacaoReserva.RESERVADO
            );
            reserva.reservar();
        });

        assertEquals("Esta reserva ja esta reservada", throwable.getMessage());
    }

    @Test
    void deveCancelarReservarCasoSituacaoReservado() throws BusinessException{
        final Reserva reserva = new Reserva(
            UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            LocalDateTime.now(), 
            SituacaoReserva.RESERVADO
        );
        reserva.cancelar();

        assertNotNull(reserva);
        assertEquals(SituacaoReserva.CANCELADO, reserva.getSituacao());
    }

    @Test
    void naoDeveCancelarUmaReservaCasoSituacaoCancelada() throws BusinessException{
        final Throwable throwable = assertThrows(BusinessException.class, () ->{
            final Reserva reserva = new Reserva(
                UUID.randomUUID(), 
                new Usuario("fualno@teste.com"), 
                new Restaurante("12345678900000"), 
                LocalDateTime.now(), 
                SituacaoReserva.CANCELADO
            );

            reserva.cancelar();
        });

        assertEquals("Esta reserva ja esta cancelada", throwable.getMessage());
    }

    @Test
    void naoDeveCancelarUmaReservaCasoSituacaoDisponivel() throws BusinessException{
        final Throwable throwable = assertThrows(BusinessException.class, () ->{
            final Reserva reserva = new Reserva(
                UUID.randomUUID(), 
                new Usuario("fualno@teste.com"), 
                new Restaurante("12345678900000"), 
                LocalDateTime.now(), 
                SituacaoReserva.DISPONIVEL
            );

            reserva.cancelar();
        });

        assertEquals("Não é possivel cancelar reserva uma disponivel", throwable.getMessage());
    }
    
    @Test
    void deveValidarReservaIgualCasoMesmaReserva() throws BusinessException {
    	final UUID randomUUID = UUID.randomUUID();
		final Reserva reserva1 = new Reserva(
            randomUUID, 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            LocalDateTime.now(), 
            SituacaoReserva.DISPONIVEL
        );
		
		final Reserva reserva2 = new Reserva(
	            randomUUID, 
	            new Usuario("fualno@teste.com"), 
	            new Restaurante("12345678900000"), 
	            LocalDateTime.now(), 
	            SituacaoReserva.DISPONIVEL
	        );
    	assertTrue(reserva1.equals(reserva2));
    	assertTrue(reserva1.equals(reserva1));
    }
    
    @Test
    void naoDeveValidarReservasIgualCasoDiferentes() throws BusinessException {
		final Reserva reserva1 = new Reserva(
			UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            LocalDateTime.now(), 
            SituacaoReserva.DISPONIVEL
        );
		
    	assertFalse(reserva1.equals(null));
    	assertFalse(reserva1.equals(new Object()));
    }
    
    @Test
    void naoDeveConcluirReservaComStatusCancelada() throws BusinessException {
    	final Throwable throwable = assertThrows(BusinessException.class, () -> {
    		final Reserva reserva = new Reserva(
				UUID.randomUUID(), 
	            new Usuario("fualno@teste.com"), 
	            new Restaurante("12345678900000"), 
	            LocalDateTime.now(), 
	            SituacaoReserva.CANCELADO
	        );
	    	
	    	reserva.concluir();
    	});
    	
    	assertEquals("Esta reserva ja esta cancelada", throwable.getMessage());
    }
    
    @Test
    void naoDeveConcluirReservaComStatusConcluido() {
    	final Throwable throwable = assertThrows(BusinessException.class, () -> {
    		final Reserva reserva = new Reserva(
				UUID.randomUUID(), 
	            new Usuario("fualno@teste.com"), 
	            new Restaurante("12345678900000"), 
	            LocalDateTime.now(), 
	            SituacaoReserva.CONCLUIDO
	        );
	    	
	    	reserva.concluir();
    	});
    	
    	assertEquals("Esta reserva ja esta concluida", throwable.getMessage());
    }
    
    @Test
    void naoDeveConcluirReservaComStatusDisponivel() {
    	final Throwable throwable = assertThrows(BusinessException.class, () -> {
    		final Reserva reserva = new Reserva(
				UUID.randomUUID(), 
	            new Usuario("fualno@teste.com"), 
	            new Restaurante("12345678900000"), 
	            LocalDateTime.now(), 
	            SituacaoReserva.DISPONIVEL
	        );
	    	
	    	reserva.concluir();
    	});
    	
    	assertEquals("Não é possivel cancelar reserva uma disponivel", throwable.getMessage());
    }
    
    @Test
    void deveConcluirReservaComStatusReservado() throws BusinessException {
    	final Reserva reserva = new Reserva(
			UUID.randomUUID(), 
            new Usuario("fualno@teste.com"), 
            new Restaurante("12345678900000"), 
            LocalDateTime.now(), 
            SituacaoReserva.RESERVADO
        );
    	
    	reserva.concluir();    	
    	assertEquals(SituacaoReserva.CONCLUIDO, reserva.getSituacao());
    }
    
    
}

package com.fiap.reserva.application.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.spring.Controller.Dto.ReservaDto;

class ReservaControllerApplicationTest {

	@InjectMocks
	private ReservaControllerApplication controllerApplication;
	
	private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

	@Test
	void toDTODeveRetornarNullCasoParametroNull() {
		assertNull(controllerApplication.toDTO(null));
	}
	
	@Test
	void toDTODeveReservaDto() throws BusinessException {
		final Reserva reserva = new Reserva(
			UUID.randomUUID(), 
			new Usuario("denis.benjamim@gmail.com"), 
			new Restaurante("12345678900000"), 
			LocalDateTime.now(),
			SituacaoReserva.CANCELADO 
		);
		
		final ReservaDto dto = controllerApplication.toDTO(reserva);
		
		assertNotNull(dto);
	}

}

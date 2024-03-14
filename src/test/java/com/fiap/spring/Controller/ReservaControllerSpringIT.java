package com.fiap.spring.Controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.spring.Controller.Dto.ReservaDto;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;




@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReservaControllerSpringIT {

	@LocalServerPort
	private int porta;

	@BeforeEach
	void setUp() {
		RestAssured.port = porta;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	@Nested
	class ConcluirReserva{
		@Test
		void deveConcluirReserva() throws Exception {
			final String numeroReservaReservada = "cd5b81eb-1228-4c80-92e6-dc05b18d5e89";
			
			given()	
				.when()
					.patch("/reserva/concluir/{numeroReserva}", numeroReservaReservada)
				.then()
					.statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT)
				;
		}
	}
	
	@Nested
	class CadastrarReserva{
		@Test
		void deveCriarReserva() throws Exception {
			final ReservaDto reservaDto = new ReservaDto(
				null, 
				"denis.benjamim@gmail.com", 
				"71736952000116", 
				LocalDateTime.of(2024, 3, 10, 12, 0), 
				null
			);
			
			final ReservaDto retorno = given()	
				.body(reservaDto)
				.contentType(ContentType.JSON)
				.when()
					.post("/reserva")
				.then()
					.statusCode(org.apache.http.HttpStatus.SC_CREATED)
//					.body("numeroReserva", is(notNull()))
				.body("statusReserva", is(SituacaoReserva.RESERVADO.toString()))
				.extract().as(ReservaDto.class);
			
			assertNotNull(retorno.numeroReserva());
		}
	}
	
	@Nested
	class BuscarReserva{
		@Test
		void deveBuscarReservasPeloUsuario() throws Exception {
			final String emailUsuario = "denis.benjamim@gmail.com";
			given()	
				.pathParam("email", emailUsuario)		
			.when()
				.get("/reserva/usuario/{email}")
			.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				;
		}
	}
}

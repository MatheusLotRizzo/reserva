package com.fiap.spring.conf;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.reserva.application.controller.RestauranteControllerApplication;
import com.fiap.reserva.application.controller.UsuarioControllerApplication;
import com.fiap.reserva.application.service.EnderecoService;
import com.fiap.reserva.application.service.HorarioSuncionamentoService;
import com.fiap.reserva.application.service.RestauranteService;
import com.fiap.reserva.application.service.UsuarioService;
import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.infra.exception.TechnicalException;
import com.fiap.reserva.infra.jdbc.restaurante.EnderecoRepositoryImpl;
import com.fiap.reserva.infra.jdbc.restaurante.HorarioFuncionamentoRepositoryImpl;
import com.fiap.reserva.infra.jdbc.restaurante.RestauranteRepositoryImpl;
import com.fiap.reserva.infra.jdbc.usuario.UsuarioRepositoryImpl;

@Configuration
public class InjecaoDependencia {
	
	@Bean
	public Connection getConnection(@Autowired DataSource dataSource) throws SQLException {
		return dataSource.getConnection();
	}
	
	//USUARIO
	@Bean
	public UsuarioRepository getUsuarioRepository(@Autowired Connection connection){
		return new UsuarioRepositoryImpl(connection);
	}
	
	@Bean
	public UsuarioService getUsuarioService(@Autowired UsuarioRepository repository) {
		return new UsuarioService(repository);
	}
	
	@Bean
	public UsuarioControllerApplication getUsuarioControllerApplication(@Autowired UsuarioService service) {
		return new UsuarioControllerApplication(service);
	}
	
	//RESTAURANTE
	@Bean
	public RestauranteRepository getRestauranteRepository(@Autowired Connection connection) {
		return new RestauranteRepositoryImpl(connection);
	}
	
	@Bean
	public RestauranteService getRestauranteService(
		@Autowired RestauranteRepository repository, 
		@Autowired EnderecoService enderecoService, 
		@Autowired HorarioSuncionamentoService horarioSuncionamentoService
	) {
		return new RestauranteService(repository, enderecoService, horarioSuncionamentoService);
	}
	
	@Bean 
	public RestauranteControllerApplication getRestauranteControllerApplication(@Autowired RestauranteService service) {
		return new RestauranteControllerApplication(service);
	}
	
	//ENDERECO
	@Bean
	public EnderecoRepository getEnderecoRepository(@Autowired Connection connection) {
		return new EnderecoRepositoryImpl(connection);
	}
	
	@Bean
	public EnderecoService getEnderecoService(@Autowired EnderecoRepository repository) {
		return new EnderecoService(repository);
	}
	
	@Bean
	public HorarioFuncionamentoRepository getHorarioFuncionamentoRepository(@Autowired Connection connection) {
		return new HorarioFuncionamentoRepositoryImpl(connection);
	}
	
	@Bean
	public HorarioSuncionamentoService getHorarioSuncionamentoService(@Autowired HorarioFuncionamentoRepository repository) {
		return new HorarioSuncionamentoService(repository);
	}
	
}

package com.fiap.spring.conf;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.fiap.reserva.application.controller.AvaliacaoControllerApplication;
import com.fiap.reserva.application.service.*;
import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.*;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.infra.jdbc.avaliacao.AvaliacaoRepositoryImpl;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.reserva.application.controller.ReservaControllerApplication;
import com.fiap.reserva.application.controller.RestauranteControllerApplication;
import com.fiap.reserva.application.controller.UsuarioControllerApplication;
import com.fiap.reserva.infra.jdbc.reserva.ReservaRepositoryImpl;
import com.fiap.reserva.infra.jdbc.restaurante.EnderecoRepositoryImpl;
import com.fiap.reserva.infra.jdbc.restaurante.HorarioFuncionamentoRepositoryImpl;
import com.fiap.reserva.infra.jdbc.restaurante.RestauranteRepositoryImpl;
import com.fiap.reserva.infra.jdbc.usuario.UsuarioRepositoryImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Configuration
public class InjecaoDependencia {
	
	//USUARIO
	@Bean
	public UsuarioRepository getUsuarioRepository(@Autowired DataSource dataSource) throws SQLException{
		return new UsuarioRepositoryImpl(dataSource.getConnection());
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
	public RestauranteRepository getRestauranteRepository(@Autowired DataSource dataSource) throws SQLException {
		return new RestauranteRepositoryImpl(dataSource.getConnection());
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
	public EnderecoRepository getEnderecoRepository(@Autowired DataSource dataSource) throws SQLException {
		return new EnderecoRepositoryImpl(dataSource.getConnection());
	}
	
	@Bean
	public EnderecoService getEnderecoService(@Autowired EnderecoRepository repository) {
		return new EnderecoService(repository);
	}
	
	@Bean
	public HorarioFuncionamentoRepository getHorarioFuncionamentoRepository(@Autowired DataSource dataSource) throws SQLException {
		return new HorarioFuncionamentoRepositoryImpl(dataSource.getConnection());
	}
	
	@Bean
	public HorarioSuncionamentoService getHorarioSuncionamentoService(@Autowired HorarioFuncionamentoRepository repository) {
		return new HorarioSuncionamentoService(repository);
	}
	
	//RESERVA
	@Bean
	public ReservaRepository getReservaRepository(@Autowired DataSource dataSource) throws SQLException {
		return new ReservaRepositoryImpl(dataSource.getConnection());
	}
	
	@Bean
	public ReservaService getReservaService(
		@Autowired ReservaRepository repository, 
		@Autowired RestauranteService restauranteService, 
		@Autowired UsuarioService usuarioService
	) {
		return new ReservaService(repository, restauranteService, usuarioService);
	}
	
	@Bean
	public ReservaControllerApplication getReservaControllerApplication(@Autowired ReservaService service) {
		return new ReservaControllerApplication(service);
	}

	//AVALIACAO
	@Bean
	public AvaliacaoRepository getAvaliacaoRepository(@Autowired DataSource dataSource) throws SQLException{
		return new AvaliacaoRepositoryImpl(dataSource.getConnection());
	}

	@Bean
	public AvaliacaoControllerApplication getAvaliacaoControllerApplication(@Autowired AvaliacaoService service) {
		return new AvaliacaoControllerApplication(service);
	}

	@Bean
	public AvaliacaoService getAvaliacaoService(@Autowired AvaliacaoRepository repository,
												@Autowired RestauranteService restauranteService,
												@Autowired UsuarioService usuarioService) {
		return new AvaliacaoService(repository,restauranteService,usuarioService);
	}

}

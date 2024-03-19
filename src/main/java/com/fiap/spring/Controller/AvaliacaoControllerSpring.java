package com.fiap.spring.Controller;

import com.fiap.spring.infra.Utils;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerCreate;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerOk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.reserva.application.controller.AvaliacaoControllerApplication;
import com.fiap.spring.Controller.Dto.AvaliacaoDto;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Avaliação", description = "Avaliação do usuário após reserva")
@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoControllerSpring {

    @Autowired
    private AvaliacaoControllerApplication avaliacaoController;

    @PostMapping
    @Operation(summary = "Realiza avaliação")
    @ApiResponseSwaggerCreate
    public ResponseEntity<?> avaliar(@RequestBody AvaliacaoDto avaliacaoDto){
        return Utils.response(HttpStatus.CREATED,
                () -> avaliacaoController.avaliar(avaliacaoDto));
    }

    @GetMapping("/{cnpj}")
    @Operation(summary = "Busca Avaliação")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarTodasPorCnpj(@PathVariable @ApiParam(value = "Cnpj do restaurante", example = "11 caracteres alfanumericos")
                                                    String cnpj) {
        return Utils.response(HttpStatus.OK,
                () -> avaliacaoController.getBuscarTodasAvaliacoesRestaurantePeloCNPJ(cnpj));
    }
}

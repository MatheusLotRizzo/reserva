package com.fiap.spring.Controller;

import com.fiap.spring.infra.Utils;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerCreate;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerNoContent;
import com.fiap.spring.swagger.annotations.ApiResponseSwaggerOk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.reserva.application.controller.RestauranteControllerApplication;
import com.fiap.spring.Controller.Dto.RestauranteDto;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurante", description = "Restaurante que recebera as reservas")
@RestController
@RequestMapping("/restaurante")
public class RestauranteControllerSpring {

	@Autowired
    private RestauranteControllerApplication restauranteController;


    @PostMapping
    @Operation(summary = "Cria um restaurante")
    @ApiResponseSwaggerCreate
    public ResponseEntity<?> criarRestaurante(@RequestBody RestauranteDto restauranteDto){
        return Utils.response(HttpStatus.CREATED, () -> restauranteController.cadastrar(restauranteDto));
    }

    @PutMapping
    @Operation(summary = "Altera um restaurante")
    @ApiResponseSwaggerCreate
    public ResponseEntity<?> alterarRestaurante(@RequestBody RestauranteDto restauranteDto ){
        return Utils.response(HttpStatus.CREATED, () -> restauranteController.alterar(restauranteDto));
    }

    @DeleteMapping("/{cnpj}")
    @Operation(summary = "Deleta um restaurante")
    @ApiResponseSwaggerNoContent
    public ResponseEntity<?> excluirRestaurante(@PathVariable @ApiParam(value = "Cnpj", example = "11 caracteres alfanumericos") String cnpj ){
        return Utils.response(HttpStatus.NO_CONTENT, () -> {
            restauranteController.excluir(cnpj);
            return null;
        });
    }

    @GetMapping("/{cnpj}")
    @Operation(summary = "Busca restaurante")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarPorCnpj(@PathVariable @ApiParam(value = "Cnpj", example = "11 caracteres alfanumericos") String cnpj) {
        return Utils.response(HttpStatus.OK, () -> restauranteController.getBuscarPor(cnpj));
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Busca restaurante por nome")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarPorNome(@PathVariable @ApiParam(value = "nome", example = "nome do restaurante") String nome) {
        return Utils.response(HttpStatus.OK, () -> restauranteController.getBuscarPorNome(nome));
    }

    @GetMapping("/tipo-cozinha/{tipoCozinha}")
    @Operation(summary = "Busca restaurante por tipo de cozinha")
    @ApiResponseSwaggerOk
    public ResponseEntity<?> buscarPorTipoCozinha(@PathVariable @ApiParam(value = "Status da entidade", example = "ITALIANA",  allowableValues = "ITALIANA,JAPONESABRASILEIRA,FRANCESA,MEXICANA,VEGANA,VEGETARIANA") String tipoCozinha) {
        return Utils.response(HttpStatus.OK, () -> restauranteController.getBuscarPorTipoCozinha(tipoCozinha));
    }
}

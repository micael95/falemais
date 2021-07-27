package br.com.uaubox.falemais.controller;

import br.com.uaubox.falemais.domain.usecases.LoadPlan;
import br.com.uaubox.falemais.dto.response.*;
import br.com.uaubox.falemais.exception.InvalidParamException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/simulation/plans")
@Api(tags = "Simulação", description = "Api utilizada para dados referente à simulação")
public class LoadPlansController {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoadPlansController.class);

    private final LoadPlan loadPlan;

    public LoadPlansController(LoadPlan loadPlan) {
        this.loadPlan = loadPlan;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Endpoint utilizado para retornar os planos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Planos encontrados com sucesso", response = CustomerResponse.class),
            @ApiResponse(code = 401, message = "Token de acesso inválido", response = ApiResponseMessage.class),
            @ApiResponse(code = 500, message = "Erro interno no servidor", response = ApiResponseMessage.class),
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> handle() {
        try {
            List<PlanResponse> planResponseList = this.loadPlan.load();
            return new ResponseEntity<>(planResponseList, HttpStatus.OK);
        } catch (Exception ex) {
            return ErrorResponse.handle(ex, LOGGER);
        }
    }

}
package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.model.Liga;
import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.service.LigaService;
import gt.edu.apuestasmundial.utils.Mensaje;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Liga", description = "Controlador para administrar las ligas")
@RestController
@RequestMapping("/liga")
public class LigaController {

    @Autowired
    LigaService ligaService;

    @Operation(
            summary = "Crear una nueva liga",
            description = "Crear una nueva liga asociada a un usuario",
            tags = {"Liga"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liga creada",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Liga.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Descripción inválida proporcionada",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "Liga-400",
                                                            value = "{\"mensaje\": \"Descripción inválida\"}"
                                                    )
                                            },
                                            schema = @Schema(implementation = Mensaje.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }

    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Liga body){
        if(body.getNombre().isEmpty() || body.getNombre().isBlank())
            return new ResponseEntity(new Mensaje("Nombre de la liga no puede ir en blanco"), HttpStatus.BAD_REQUEST);
        if(body.getValorIngreso() <= 0.0)
            return new ResponseEntity(new Mensaje("El valor de ingreso debe ser mayor a cero"), HttpStatus.BAD_REQUEST);

        Liga liga = new Liga();
        liga.setNombre(body.getNombre());
        liga.setValorIngreso(body.getValorIngreso());
        // Guardamos el registro
        ligaService.save(liga);

        return new ResponseEntity(new Mensaje("Liga creada con éxito!!"), HttpStatus.OK);

    }

}

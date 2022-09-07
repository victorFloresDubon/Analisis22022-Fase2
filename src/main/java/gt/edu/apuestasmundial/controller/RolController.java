package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.service.RolService;
import gt.edu.apuestasmundial.utils.Mensaje;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Rol", description = "Operaciones para la tabla Rol")
@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired RolService rolService;

    @GetMapping("/")
    @Operation(
            summary = "Recupera todos los roles registrados",
            description = "Obtiene una lista de tipo Rol de todos los registros existentes",
            tags = {"Rol"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema (
                                                            implementation = Rol.class
                                                    )
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }
    )
    public ResponseEntity<List<Rol>> getAll() {
        List<Rol> list = rolService.getAll();
        if(list.isEmpty()){
            return new ResponseEntity(new Mensaje("No existen datos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Rol>>(list, HttpStatus.OK);
    }

}

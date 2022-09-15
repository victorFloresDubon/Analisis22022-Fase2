package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.model.EstadoInvitacion;
import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.service.EstadoInvitacionService;
import gt.edu.apuestasmundial.utils.Mensaje;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Estatus de Invitaciones", description = "Controlador para los estatus de las invitaciones")
@RestController
@RequestMapping("/estadoinvitacion")
public class EstadoInvitacionController {

    @Autowired
    EstadoInvitacionService estadoInvitacionService;

    @Operation(
            summary = "Listar estatus de invitaciones registrados",
            description = "Obtiene una todos los registros de estatus de invitaciones",
            tags = {"Estatus de Invitaciones"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registros encontrados",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = EstadoInvitacion.class
                                                    )
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = "No se encontraron registros"),
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }
    )
    @GetMapping("/")
    public ResponseEntity<List<EstadoInvitacion>> getAll(){
        List<EstadoInvitacion> list = estadoInvitacionService.getAll();
        if(list.isEmpty())
            return new ResponseEntity(new Mensaje("No existen datos"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<EstadoInvitacion>>(list, HttpStatus.OK);

    }

    @Operation(
            summary = "Obtener estatus de invitación por ID",
            description = "Búsqueda de un estatus de invitación por medio de ID",
            tags = {"Estatus de Invitaciones"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registro obtenido",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = EstadoInvitacion.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Registro no encontrado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Mensaje.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }

    )
    @GetMapping("/findby")
    public ResponseEntity<EstadoInvitacion> getById(@RequestParam(name = "id") Long id){
        if(!estadoInvitacionService.existsById(id))
            return new ResponseEntity(new Mensaje("Registro con ID "+id+" no existe"), HttpStatus.NOT_FOUND);
        EstadoInvitacion estadoInvitacion = estadoInvitacionService.getById(id);
        return new ResponseEntity<EstadoInvitacion>(estadoInvitacion, HttpStatus.OK);
    }

    @Operation(
            summary = "Crear un nuevo estatus de invitaciones",
            description = "Crear un nuevo estatus para las invitaciones a ligas",
            tags = {"Estatus de Invitaciones"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estatus creado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Rol.class)
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
                                                            name = "EstatusInvitacion-400",
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
    public ResponseEntity<?> save (@RequestBody EstadoInvitacion body){
        if(body.getDescripcion().isBlank() || body.getDescripcion().isEmpty())
            return new ResponseEntity(new Mensaje("Descripción no puede ir vacía o en blanco"), HttpStatus.BAD_REQUEST);
        EstadoInvitacion estadoInvitacion = new EstadoInvitacion();
        estadoInvitacion.setDescripcion(body.getDescripcion());
        estadoInvitacionService.save(estadoInvitacion);
        return new ResponseEntity<EstadoInvitacion>(estadoInvitacion, HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar un estatus de invitación existente",
            description = "Este servicio permite actualizar la información de un estatus existente",
            tags = {"Estatus de Invitaciones"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registro actualizado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = EstadoInvitacion.class)
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
                                                            name = "EstatusInvitacion-400",
                                                            value = "{\"mensaje\": \"Descripción inválida\"}"
                                                    )
                                            },
                                            schema = @Schema(implementation = Mensaje.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estatus de invitación no encontrado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "EstatusInvitacion-404",
                                                            value = "{\"mensaje\": \"Registro no encontrado\"}"
                                                    )
                                            },
                                            schema = @Schema(implementation = Mensaje.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }

    )
    @PutMapping
    public ResponseEntity<?> update (@RequestBody EstadoInvitacion body){
        if(!estadoInvitacionService.existsById(body.getId()))
            return new ResponseEntity(new Mensaje("ID del registro no existe"), HttpStatus.BAD_REQUEST);
        if(body.getDescripcion().isBlank() || body.getDescripcion().isEmpty())
            return new ResponseEntity(new Mensaje("Descripción no puede ir vacía o en blanco"), HttpStatus.BAD_REQUEST);
        EstadoInvitacion estadoInvitacion = estadoInvitacionService.getById(body.getId());
        estadoInvitacion.setDescripcion(body.getDescripcion());
        estadoInvitacionService.save(estadoInvitacion);
        return new ResponseEntity<EstadoInvitacion>(estadoInvitacion, HttpStatus.OK);
    }

    @Operation(
            summary = "Elimina un estatus de invitación existente",
            description = "Esta acción permite eliminar un estatus de invitación existente.",
            tags = {"Estatus de Invitaciones"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estatus de invitaciones eliminado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "EstatusInvitacion-200",
                                                            value = "{\"mensaje\": \"Registro eliminado exitosamente! ID: 1\"}"
                                                    )
                                            },
                                            schema = @Schema(implementation = Rol.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estatus de invitación no encontrado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "EstatusInvitacion-404",
                                                            value = "{\"mensaje\": \"Registro no encontrado\"}"
                                                    )
                                            },
                                            schema = @Schema(implementation = Mensaje.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }

    )
    @DeleteMapping
    public ResponseEntity<?> delete (@RequestParam(name = "id") Long id){
        if(!estadoInvitacionService.existsById(id))
            return new ResponseEntity(new Mensaje("ID del registro no existe"), HttpStatus.BAD_REQUEST);
        estadoInvitacionService.delete(id);
        return new ResponseEntity(new Mensaje("Registro eliminado exitosamente! ID: "+id), HttpStatus.OK);
    }

}

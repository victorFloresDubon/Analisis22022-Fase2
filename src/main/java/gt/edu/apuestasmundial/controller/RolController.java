package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.service.RolService;
import gt.edu.apuestasmundial.utils.Mensaje;
import io.swagger.annotations.Example;
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

@Tag(name = "Rol", description = "Operaciones para la tabla Rol")
@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired RolService rolService;
    @GetMapping("/")
    @Operation(
            summary = "Listar todos los roles registrados",
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

    @GetMapping("/find")
    @Operation(
            summary = "Obtener un rol por ID",
            description = "Búsqueda de un regisro del tipo Rol por medio de ID",
            tags = {"Rol"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Rol.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
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
    public ResponseEntity<Rol> getById(@RequestParam(name = "id") Integer id){
        // Si NO existe por ID Entonces devuelve código 404
        if(!rolService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe registro con ID: " + id), HttpStatus.NOT_FOUND);
        }
        Rol response = rolService.getById(id);
        return new ResponseEntity<Rol>(response, HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo rol",
            description = "Por medio de este servicio se podrá crear un nuevo rol que será ocupado para " +
                    "ser usado en la funcionalidad del sitio",
            tags = {"Rol"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
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
                                                            name = "RolService-400",
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
    public ResponseEntity<?> save(@RequestBody Rol body){
        // Si la descripción es vacía o en blanco entonces retorna BAD REQUEST
        if(body.getNombre().isEmpty() || body.getNombre().isBlank()){
            return new ResponseEntity(new Mensaje("Descripción es campo requerido"), HttpStatus.BAD_REQUEST);
        }
        rolService.save(body);
        return new ResponseEntity<Rol>(body, HttpStatus.OK);
    }

    @PutMapping
    @Operation(
            summary = "Actualizar un rol existente",
            description = "Por medio de este servicio se podrá actualizar la información de un rol existente.",
            tags = {"Rol"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
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
                                                            name = "RolService-400",
                                                            value = "{\"mensaje\": \"Descripción inválida\"}"
                                                    )
                                            },
                                            schema = @Schema(implementation = Mensaje.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rol no encontrado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "RolService-404",
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
    public ResponseEntity<?> update(
            @RequestParam(name = "id") Integer id,
            @RequestBody Rol body
    ){
        // Si el ID del rol NO existe entonces retorna BAD REQUEST
        if(!rolService.existsById(id))
            return new ResponseEntity(new Mensaje("ID del registro no existe"), HttpStatus.NOT_FOUND);
        // Si la descripción es vacía o en blanco entonces retorna BAD REQUEST
        if(body.getNombre().isEmpty() || body.getNombre().isBlank()){
            return new ResponseEntity(new Mensaje("Descripción es campo requerido"), HttpStatus.BAD_REQUEST);
        }
        // Obtiene el registro por medio de ID
        Rol rol = rolService.getById(id);
        // Asigna los nuevos valores al registro
        rol.setNombre(body.getNombre());
        // Guarda los cambios realizados
        rolService.save(rol);
        return new ResponseEntity<Rol>(rol, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(
            summary = "Elimina un rol existente",
            description = "Por medio de este servicio se podrá eliminar la información de un rol existente.",
            tags = {"Rol"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "RolService-200",
                                                            value = "{\"mensaje\": \"Registro eliminado exitosamente! ID: 1\"}"
                                                    )
                                            },
                                            schema = @Schema(implementation = Rol.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rol no encontrado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                    @ExampleObject(
                                                            name = "RolService-404",
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
    public ResponseEntity<?> delete(
            @RequestParam(name = "id") Integer id
    ){
        // Si el ID del rol NO existe entonces retorna BAD REQUEST
        if(!rolService.existsById(id))
            return new ResponseEntity(new Mensaje("ID del registro no existe"), HttpStatus.NOT_FOUND);
        rolService.delete(id);
        return new ResponseEntity(new Mensaje("Registro eliminado exitosamente! ID: "+id), HttpStatus.OK);
    }
}

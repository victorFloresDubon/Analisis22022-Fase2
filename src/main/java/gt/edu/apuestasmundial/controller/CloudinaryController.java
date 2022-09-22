package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.service.CloudinaryService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Tag(name = "Cloudinary", description = "Controlador para la interfaz con Cloudinary")
@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {

    @Autowired
    CloudinaryService cloudinaryService;

    @Operation(
            summary = "Cargar una nueva imágen",
            description = "Carga una nueva imágen al repositorio en Cloudinary",
            tags = {"Cloudinary"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Imágen cargada",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Map.class)
                                    )
                            }
                    ),/*
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se pudo cargar la ",
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
                    ),*/
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }

    )
    @PostMapping("/upload")
    public ResponseEntity<Map> upload(@RequestParam(name = "file") MultipartFile multipartFile){
        Map result = cloudinaryService.upload(multipartFile);
        return new ResponseEntity<Map>(result, HttpStatus.OK);
    }

    @Operation(
            summary = "Borrar una imágen",
            description = "Se borra una imágen por medio de su ID provisto por Cloudinary",
            tags = {"Cloudinary"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Imágen borrada",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Map.class)
                                    )
                            }
                    ),/*
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se pudo cargar la ",
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
                    ),*/
                    @ApiResponse(responseCode = "500", description = "Internal Error")
            }

    )
    @DeleteMapping("/delete")
    public ResponseEntity<Map> delete(@RequestParam(name = "id") String id){
        Map result = cloudinaryService.delete(id);
        return new ResponseEntity<Map>(result, HttpStatus.OK);
    }

}

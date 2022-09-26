package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.model.FotoUsuario;
import gt.edu.apuestasmundial.model.Usuario;
import gt.edu.apuestasmundial.service.CloudinaryService;
import gt.edu.apuestasmundial.service.FotoUsuarioService;
import gt.edu.apuestasmundial.service.UsuarioService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Tag(name = "Cloudinary", description = "Controlador para la interfaz con Cloudinary")
@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    FotoUsuarioService fotoUsuarioService;
    @Autowired
    UsuarioService usuarioService;

    @Operation(
            summary = "Listar fotos de usuario",
            description = "Devuelve un listado de todas las imagenes de perfil de los usuarios",
            tags = {"Cloudinary"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registros recuperados",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = FotoUsuario.class
                                                    )
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron registros",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Mensaje.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<List<FotoUsuario>> getAll(){
        List<FotoUsuario> list = fotoUsuarioService.getAll();
        if(list.isEmpty())
            return new ResponseEntity(new Mensaje("No se encontraron datos"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<FotoUsuario>>(list, HttpStatus.OK);
    }

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
    public ResponseEntity<?> upload(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "file") MultipartFile multipartFile
    ){
        try{
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if(bi == null)
                return new ResponseEntity(new Mensaje("Imagen no válida"), HttpStatus.BAD_REQUEST);
        } catch (IOException e){
            return new ResponseEntity(new Mensaje("Algo salió mal al leer el archivo"), HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        // Recupera la información obtenida del resultado para crear la imagen
        FotoUsuario fotoUsuario = new FotoUsuario();
        fotoUsuario.setUrl((String) result.get("url"));
        fotoUsuario.setNombre((String) result.get("original_filename"));
        fotoUsuario.setPublicId((String) result.get("public_id"));
        // Obtenemos el registro del usuario por medio de su ID
        Usuario usuario = usuarioService.getById(userId);
        fotoUsuario.setUsuario(usuario);
        // Guardamos la imagen
        fotoUsuarioService.save(fotoUsuario);
        return new ResponseEntity(new Mensaje("Imagen subida con éxito"), HttpStatus.OK);
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
    public ResponseEntity<?> delete(@RequestParam(name = "id") String id){
        Map result = cloudinaryService.delete(id);
        return new ResponseEntity<Map>(result, HttpStatus.OK);
    }

}

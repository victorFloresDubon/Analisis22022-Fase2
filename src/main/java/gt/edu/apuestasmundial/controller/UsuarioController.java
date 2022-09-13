package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.dto.NuevoUsuario;
import gt.edu.apuestasmundial.model.ERol;
import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.model.Usuario;
import gt.edu.apuestasmundial.security.jwt.JwtProvider;
import gt.edu.apuestasmundial.service.RolService;
import gt.edu.apuestasmundial.service.UsuarioService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Usuario", description = "Operaciones para el control de usuarios")
@RestController
@RequestMapping("/auth")
public class UsuarioController {


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtTProvider;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Da de alta a un nuevo usuario del sistema",
            tags = {"Usuario"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado con éxito",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Mensaje.class),
                                            examples = @ExampleObject(
                                                    name = "Usuario-200",
                                                    value = "{\"mensaje\": \"Usuario creado con éxito\"}"
                                            )
                                    )
                            }
                    )
            }
    )
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos deben ir llenos o nombre de usuario inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombre(nuevoUsuario.getNombre()))
            return new ResponseEntity(new Mensaje("Usuario ya existe con ese correo, intente de nuevo"), HttpStatus.BAD_REQUEST);

        // Instancia un nuevo usuario luego de ser validado anteriormente
        Usuario usuario = new Usuario();
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        List<Rol> roles = new ArrayList<>();
        // Agrega el rol ROLE_USER por defecto
        roles.add(rolService.findByNombre(ERol.ROL_USUARIO.name()).get());
        // Asocia los roles al usuario nuevo
        usuario.setRoles(roles);
        // Registra la información del nuevo usuario
        usuarioService.save(usuario);

        return new ResponseEntity(new Mensaje("Usuario creado exitosamente!!"), HttpStatus.OK);
    }


}

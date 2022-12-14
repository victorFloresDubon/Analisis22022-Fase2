package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.dto.ChangePassword;
import gt.edu.apuestasmundial.dto.LoginUsuario;
import gt.edu.apuestasmundial.dto.NuevoUsuario;
import gt.edu.apuestasmundial.model.ERol;
import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.model.Usuario;
import gt.edu.apuestasmundial.security.jwt.JwtProvider;
import gt.edu.apuestasmundial.service.RolService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Usuario", description = "Operaciones para el control de usuarios")
@RestController
@CrossOrigin
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
                            description = "Usuario registrado con ??xito",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Mensaje.class),
                                            examples = @ExampleObject(
                                                    name = "Usuario-200",
                                                    value = "{\"mensaje\": \"Usuario creado con ??xito\"}"
                                            )
                                    )
                            }
                    )
            }
    )
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos deben ir llenos o nombre de usuario inv??lido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombre(nuevoUsuario.getNombre()))
            return new ResponseEntity(new Mensaje("Usuario ya existe con ese correo"), HttpStatus.BAD_REQUEST);

        // Instancia un nuevo usuario luego de ser validado anteriormente
        Usuario usuario = new Usuario();
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        List<Rol> roles = new ArrayList<>();
        // Agrega el rol ROLE_USER por defecto
        roles.add(rolService.findByNombre(ERol.ROLE_USUARIO).get());
        // Asocia los roles al usuario nuevo
        usuario.setRoles(roles);
        // Registra la informaci??n del nuevo usuario
        usuarioService.save(usuario);

        return new ResponseEntity(new Mensaje("Usuario creado exitosamente!!"), HttpStatus.OK);
    }

    @Operation(
            summary = "Inicio de sesi??n de un usuario registrado",
            description = "Da de alta a un nuevo usuario del sistema",
            tags = {"Usuario"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario autenticado con ??xito",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Mensaje.class),
                                            examples = @ExampleObject(
                                                    name = "Usuario-200",
                                                    value = "{\"usuario\": \"ejemplo@ejemplo.com\", \"token\":\"Ajdkshdsss\"}"
                                            )
                                    )
                            }
                    )
            }
    )
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody LoginUsuario login, BindingResult bindingResult){
        // Verifica si el formulario contiene errores
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Usuario/Clave inv??lidos"), HttpStatus.BAD_REQUEST);
        // SI el nombre de usuario es nulo o va en blanco entonces ser?? una mala solicitud
        if(login.getUsuario() == null ||login.getUsuario().isEmpty() || login.getUsuario().isBlank())
            return new ResponseEntity(new Mensaje("Debe proporcionar un nombre de usuario"), HttpStatus.BAD_REQUEST);
        // SI la clave de usuario es nula o va en blanco entonces ser?? una mala solicitud
        if(login.getPassword() == null ||login.getPassword().isEmpty() || login.getPassword().isBlank())
            return new ResponseEntity(new Mensaje("La clave de usuario no puede ir en blanco"), HttpStatus.BAD_REQUEST);

        // Autenticamos por medio del usuario y clave registrados
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(login.getUsuario(), login.getPassword())
                );
        // Solicitamos un nuevo token de sesi??n
        String token = jwtTProvider.crearToken(authentication);
        // Devolvemos la respuesta
        Map<Object, Object> res = new HashMap<>();
        res.put("usuario", login.getUsuario());
        res.put("token", token);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @Operation(
            summary = "Cambio de clave",
            description = "Permite el cambio de clave de un usuario autenticado",
            tags = {"Usuario"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contrase??a cambiada con ??xito",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Mensaje.class),
                                            examples = @ExampleObject(
                                                    name = "Usuario-200",
                                                    value = "{\"usuario\": \"ejemplo@ejemplo.com\", \"token\":\"Ajdkshdsss\"}"
                                            )
                                    )
                            }
                    )
            }
    )
    @PutMapping("/chgpwd")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword form, BindingResult bindingResult){
        // Si la nueva clave y la confirmaci??n de la misma no son iguales entonces env??a status 400
        if(!form.getConfirmedPassword().equals(form.getPassword()))
            return new ResponseEntity(
                    new Mensaje("La confirmaci??n de clave y la nueva clave deben ser los mismos"),
                    HttpStatus.BAD_REQUEST
                    );
        // Si el usuario NO existe entonces enviamos status 400
        if(!usuarioService.existsByNombre(form.getUsuario()))
            return new ResponseEntity(new Mensaje("El nombre de usuario no existe"), HttpStatus.BAD_REQUEST);
        // Recuperamos la informaci??n del usuario
        Usuario usuario = usuarioService.findByNombre(form.getUsuario()).get();
        // Asignamos la nueva clave proporcionada por el usuario
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));
        // Actualizamos la informaci??n
        usuarioService.save(usuario);

        return new ResponseEntity(new Mensaje("Clave de usuario cambiada con ??xito"), HttpStatus.OK);

    }

    @Operation(
            summary = "Obtener ID por nombre de usuario",
            description = "Este m??todo retorna el ID del usuario por medio de b??squeda por nombre",
            tags = {"Usuario"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Datos obtenidos"
                    )
            }
    )
    @GetMapping("/findByNombre")
    public ResponseEntity<Long> getByNombre(
            @RequestParam(name = "nombre") String nombre
    ){
        Long id = usuarioService.findByNombre(nombre).get().getId();
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtiene todos los usuarios",
            description = "Obtiene la informaci??n de todos los usuarios registrados",
            tags = {"Usuario"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Datos obtenidos",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = Usuario.class
                                                    )
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron datos",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = Mensaje.class
                                            )
                                    )
                            }
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> list = usuarioService.getAll();
        if(list.isEmpty())
            return new ResponseEntity(new Mensaje("No existen datos"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

}

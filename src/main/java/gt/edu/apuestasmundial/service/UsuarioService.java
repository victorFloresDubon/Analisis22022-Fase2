package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericService;
import gt.edu.apuestasmundial.model.Usuario;

import java.util.Optional;

public interface UsuarioService extends GenericService<Usuario, Long> {
    boolean existsByNombre(String nombre);
    Optional<Usuario> findByNombre(String nombre);

}

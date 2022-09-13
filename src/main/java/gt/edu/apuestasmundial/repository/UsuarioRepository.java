package gt.edu.apuestasmundial.repository;

import gt.edu.apuestasmundial.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);
    boolean existsByNombre(String nombre);

}

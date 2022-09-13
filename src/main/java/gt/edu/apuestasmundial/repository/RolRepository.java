package gt.edu.apuestasmundial.repository;

import gt.edu.apuestasmundial.model.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol, Integer> {
    @Override
    boolean existsById(Integer integer);
    boolean existsByNombre(String nombre);
    Optional<Rol> findByNombre(String nombre);
}

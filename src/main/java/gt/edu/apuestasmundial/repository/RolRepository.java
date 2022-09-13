package gt.edu.apuestasmundial.repository;

import gt.edu.apuestasmundial.model.ERol;
import gt.edu.apuestasmundial.model.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol, Integer> {
    @Override
    boolean existsById(Integer integer);
    boolean existsByNombre(ERol nombre);
    Optional<Rol> findByNombre(ERol nombre);
}

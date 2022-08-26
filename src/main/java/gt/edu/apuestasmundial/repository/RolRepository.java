package gt.edu.apuestasmundial.repository;

import gt.edu.apuestasmundial.model.Rol;
import org.springframework.data.repository.CrudRepository;

public interface RolRepository extends CrudRepository<Rol, Integer> {
    @Override
    boolean existsById(Integer integer);
}

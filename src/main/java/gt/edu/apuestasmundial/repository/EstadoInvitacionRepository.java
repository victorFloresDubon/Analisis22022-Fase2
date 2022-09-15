package gt.edu.apuestasmundial.repository;

import gt.edu.apuestasmundial.model.EstadoInvitacion;
import org.springframework.data.repository.CrudRepository;

public interface EstadoInvitacionRepository extends CrudRepository<EstadoInvitacion, Long> {

    @Override
    boolean existsById(Long id);

}

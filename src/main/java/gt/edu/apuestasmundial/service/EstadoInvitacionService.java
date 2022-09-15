package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericService;
import gt.edu.apuestasmundial.model.EstadoInvitacion;

public interface EstadoInvitacionService extends GenericService<EstadoInvitacion, Long> {

    boolean existsById(Long id);

}

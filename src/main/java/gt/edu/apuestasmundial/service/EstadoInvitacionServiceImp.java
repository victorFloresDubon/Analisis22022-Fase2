package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericServiceImp;
import gt.edu.apuestasmundial.model.EstadoInvitacion;
import gt.edu.apuestasmundial.repository.EstadoInvitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class EstadoInvitacionServiceImp extends GenericServiceImp<EstadoInvitacion, Long> implements EstadoInvitacionService {

    @Autowired
    EstadoInvitacionRepository estadoInvitacionRepository;

    @Override
    public CrudRepository<EstadoInvitacion, Long> getRepository() {
        return estadoInvitacionRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return estadoInvitacionRepository.existsById(id);
    }
}

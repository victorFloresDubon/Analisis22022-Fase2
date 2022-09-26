package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericServiceImp;
import gt.edu.apuestasmundial.model.FotoUsuario;
import gt.edu.apuestasmundial.repository.FotoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class FotoUsuarioServiceImp extends GenericServiceImp<FotoUsuario, Long> implements FotoUsuarioService {

    @Autowired
    FotoUsuarioRepository fotoUsuarioRepository;


    @Override
    public CrudRepository<FotoUsuario, Long> getRepository() {
        return fotoUsuarioRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return fotoUsuarioRepository.existsById(id);
    }
}

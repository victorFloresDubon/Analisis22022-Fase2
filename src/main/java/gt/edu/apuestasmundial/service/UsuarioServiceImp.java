package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericServiceImp;
import gt.edu.apuestasmundial.model.Usuario;
import gt.edu.apuestasmundial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImp extends GenericServiceImp<Usuario, Long> implements UsuarioService {

    @Autowired UsuarioRepository usuarioRepository;

    @Override
    public CrudRepository<Usuario, Long> getRepository() {
        return usuarioRepository;
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }

    @Override
    public Optional<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
}

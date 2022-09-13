package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericServiceImp;
import gt.edu.apuestasmundial.model.ERol;
import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Aquí va a ir toda la lógica de negocio
@Service
public class RolServiceImp extends GenericServiceImp<Rol, Integer> implements RolService{

    @Autowired RolRepository repository;

    @Override
    public CrudRepository<Rol, Integer> getRepository() {
        return repository;
    }


    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByNombre(ERol nombre) {
        return repository.existsByNombre(nombre);
    }

    @Override
    public Optional<Rol> findByNombre(ERol nombre) {
        return repository.findByNombre(nombre);
    }
}

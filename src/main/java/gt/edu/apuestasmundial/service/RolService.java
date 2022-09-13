package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericService;
import gt.edu.apuestasmundial.model.ERol;
import gt.edu.apuestasmundial.model.Rol;

import java.util.Optional;

public interface RolService extends GenericService<Rol, Integer> {
    boolean existsById(Integer integer);
    boolean existsByNombre(ERol nombre);
    Optional<Rol> findByNombre(ERol nombre);
}

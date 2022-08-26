package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericService;
import gt.edu.apuestasmundial.model.Rol;

public interface RolService extends GenericService<Rol, Integer> {
    boolean existsById(Integer integer);
}

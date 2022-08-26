package gt.edu.apuestasmundial.common;

import java.io.Serializable;
import java.util.List;

public interface GenericService <T, ID extends Serializable> {

    void save (T entity);
    T getById(ID id);
    void delete(ID id);
    List<T> getAll();

}

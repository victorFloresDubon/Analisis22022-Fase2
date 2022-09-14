package gt.edu.apuestasmundial.service;

import gt.edu.apuestasmundial.common.GenericServiceImp;
import gt.edu.apuestasmundial.model.Liga;
import gt.edu.apuestasmundial.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class LigaServiceImp extends GenericServiceImp<Liga, Long> implements LigaService{

    @Autowired
    LigaRepository ligaRepository;

    @Override
    public CrudRepository<Liga, Long> getRepository() {
        return ligaRepository;
    }
}

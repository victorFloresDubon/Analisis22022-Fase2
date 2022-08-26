package gt.edu.apuestasmundial.controller;

import gt.edu.apuestasmundial.model.Rol;
import gt.edu.apuestasmundial.service.RolService;
import gt.edu.apuestasmundial.utils.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired RolService rolService;

    @GetMapping("/")
    public ResponseEntity<List<Rol>> getAll() {
        List<Rol> list = rolService.getAll();
        if(list.isEmpty()){
            return new ResponseEntity(new Mensaje("No existen datos"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Rol>>(list, HttpStatus.OK);
    }

}

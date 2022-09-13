package gt.edu.apuestasmundial.dto;

import gt.edu.apuestasmundial.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevoUsuario {

    private String nombre;
    private String password;
    private List<Rol> roles = new ArrayList<>();

}

package gt.edu.apuestasmundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword extends LoginUsuario{

    private String confirmedPassword;

}

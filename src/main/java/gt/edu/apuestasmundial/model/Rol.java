package gt.edu.apuestasmundial.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Schema(name = "Rol", description = "Rol de usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {

    @Schema(name = "ID de rol")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Schema(name = "Nombre/Descripción del rol de usuario")
    @Column(name = "nombre")
    private String nombre;

}

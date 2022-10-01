package gt.edu.apuestasmundial.model;

import gt.edu.apuestasmundial.model.key.MiembroLigaId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "miembro_liga")
public class MiembroLiga {

    @EmbeddedId
    private MiembroLigaId id;

    @ManyToOne
    @MapsId(value = "usuarioId")
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId(value = "ligaId")
    @JoinColumn(name = "liga")
    private Liga liga;

    @ManyToOne
    @MapsId(value = "rolId")
    @JoinColumn(name = "rol")
    private Rol rol;

}

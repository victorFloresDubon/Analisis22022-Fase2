package gt.edu.apuestasmundial.model.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MiembroLigaId implements Serializable {

    @Column(name = "usuario")
    private Long usuarioId;

    @Column(name = "liga")
    private Long ligaId;

    @Column(name = "rol")
    private Long rolId;
}

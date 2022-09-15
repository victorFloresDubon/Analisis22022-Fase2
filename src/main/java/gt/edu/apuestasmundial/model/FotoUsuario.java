package gt.edu.apuestasmundial.model;

import gt.edu.apuestasmundial.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foto_usuario")
public class FotoUsuario {

    @Id
    @Column(name = "usuario")
    private Long usuarioId;

    @Column(name = "url_img")
    private String url;

    @OneToOne(mappedBy = "fotoUsuario")
    private Usuario usuario;

}

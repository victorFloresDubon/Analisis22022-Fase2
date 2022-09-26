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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url_img")
    private String url;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    private Usuario usuario;

}

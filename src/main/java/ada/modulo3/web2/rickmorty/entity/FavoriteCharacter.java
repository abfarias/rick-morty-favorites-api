package ada.modulo3.web2.rickmorty.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "favorite_character",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_favorite_character_external_id",
                columnNames = "external_character_id"
        )
)
public class FavoriteCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID do personagem na API externa (Rick and Morty).
     */
    @Column(name = "external_character_id", nullable = false)
    private Long idPersonagem;

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "comentario", nullable = false, length = 500)
    private String comentario;

    public FavoriteCharacter() {
    }

    public FavoriteCharacter(Long idPersonagem, Integer nota, String comentario) {
        this.idPersonagem = idPersonagem;
        this.nota = nota;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public Long getIdPersonagem() {
        return idPersonagem;
    }

    public void setIdPersonagem(Long idPersonagem) {
        this.idPersonagem = idPersonagem;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

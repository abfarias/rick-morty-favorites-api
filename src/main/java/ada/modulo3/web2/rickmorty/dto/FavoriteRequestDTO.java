package ada.modulo3.web2.rickmorty.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para criar/atualizar favoritos.
 * Usado em POST e PUT /api/v1/personagens/favorito/{id}
 */
public class FavoriteRequestDTO {

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é 1")
    @Max(value = 5, message = "Nota máxima é 5")
    private Integer nota;

    @NotBlank(message = "Comentário é obrigatório")
    @Size(min = 3, max = 500, message = "Comentário deve ter entre 3 e 500 caracteres")
    private String comentario;

    public FavoriteRequestDTO() {
    }

    public FavoriteRequestDTO(Integer nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
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

package ada.modulo3.web2.rickmorty.dto;

/**
 * DTO de resposta para favoritos (apenas dados do domínio).
 * Usado em POST, PUT /api/v1/personagens/favorito/{id}
 */
public class FavoriteResponseDTO {

    private Long idPersonagem;
    private Integer nota;
    private String comentario;

    public FavoriteResponseDTO() {
    }

    public FavoriteResponseDTO(Long idPersonagem, Integer nota, String comentario) {
        this.idPersonagem = idPersonagem;
        this.nota = nota;
        this.comentario = comentario;
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

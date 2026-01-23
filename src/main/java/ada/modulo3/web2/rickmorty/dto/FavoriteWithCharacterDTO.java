package ada.modulo3.web2.rickmorty.dto;

/**
 * DTO agregado: favorito + dados externos do personagem.
 * Usado em GET /api/v1/personagens/favoritos
 */
public class FavoriteWithCharacterDTO {

    // Dados no banco (nossa aplicação)
    private Long idPersonagem;
    private Integer nota;
    private String comentario;
    
    // Dados externos (API Rick and Morty)
    private String name;
    private String status;

    public FavoriteWithCharacterDTO() {
    }

    public FavoriteWithCharacterDTO(Long idPersonagem, Integer nota, String comentario, 
                                     String name, String status) {
        this.idPersonagem = idPersonagem;
        this.nota = nota;
        this.comentario = comentario;
        this.name = name;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

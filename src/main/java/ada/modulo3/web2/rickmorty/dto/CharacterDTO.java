package ada.modulo3.web2.rickmorty.dto;

/**
 * DTO para retornar dados de personagem (da API externa).
 * Usado em GET /api/v1/personagens/{id}
 */
public class CharacterDTO {

    private Long id;
    private String name;
    private String status;
    private String species;

    public CharacterDTO() {
    }

    public CharacterDTO(Long id, String name, String status, String species) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}

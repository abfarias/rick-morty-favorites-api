package ada.modulo3.web2.rickmorty.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO que espelha o retorno da Rick and Morty API para um personagem.
 * Exemplo: GET https://rickandmortyapi.com/api/character/1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RickAndMortyCharacterDTO {

    private Long id;
    private String name;
    private String status;
    private String species;
    // A API retorna mais campos (gender, origin, location, etc.), 
    // mas só mapeamos o que precisamos

    public RickAndMortyCharacterDTO() {
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

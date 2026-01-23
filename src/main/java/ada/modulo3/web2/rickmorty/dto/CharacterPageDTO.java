package ada.modulo3.web2.rickmorty.dto;

import java.util.List;

/**
 * DTO para retornar página de personagens.
 * Usado em GET /api/v1/personagens?page=1
 */
public class CharacterPageDTO {

    private Integer page;
    private List<CharacterDTO> results;

    public CharacterPageDTO() {
    }

    public CharacterPageDTO(Integer page, List<CharacterDTO> results) {
        this.page = page;
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<CharacterDTO> getResults() {
        return results;
    }

    public void setResults(List<CharacterDTO> results) {
        this.results = results;
    }
}

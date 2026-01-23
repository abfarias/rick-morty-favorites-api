package ada.modulo3.web2.rickmorty.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * DTO que espelha o retorno da Rick and Morty API para paginação.
 * Exemplo: GET https://rickandmortyapi.com/api/character?page=1
 * 
 * Formato real da API:
 * {
 *   "info": { "count": 826, "pages": 42, "next": "...", "prev": null },
 *   "results": [ {...}, {...} ]
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RickAndMortyPageDTO {

    private InfoDTO info;
    private List<RickAndMortyCharacterDTO> results;

    public RickAndMortyPageDTO() {
    }

    public InfoDTO getInfo() {
        return info;
    }

    public void setInfo(InfoDTO info) {
        this.info = info;
    }

    public List<RickAndMortyCharacterDTO> getResults() {
        return results;
    }

    public void setResults(List<RickAndMortyCharacterDTO> results) {
        this.results = results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InfoDTO {
        private Integer count;
        private Integer pages;
        private String next;
        private String prev;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getPrev() {
            return prev;
        }

        public void setPrev(String prev) {
            this.prev = prev;
        }
    }
}

package ada.modulo3.web2.rickmorty.mapper;

import ada.modulo3.web2.rickmorty.dto.CharacterDTO;
import ada.modulo3.web2.rickmorty.dto.CharacterPageDTO;
import ada.modulo3.web2.rickmorty.dto.RickAndMortyCharacterDTO;
import ada.modulo3.web2.rickmorty.dto.RickAndMortyPageDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para conversões entre DTOs da API externa (Rick and Morty) 
 * e DTOs da nossa API.
 */
@ApplicationScoped
public class CharacterMapper {

    /**
     * Converte DTO externo → DTO interno (personagem individual)
     */
    public CharacterDTO toCharacterDTO(RickAndMortyCharacterDTO externalDTO) {
        if (externalDTO == null) {
            return null;
        }
        return new CharacterDTO(
            externalDTO.getId(),
            externalDTO.getName(),
            externalDTO.getStatus(),
            externalDTO.getSpecies()
        );
    }

    /**
     * Converte DTO externo de página → DTO interno de página
     */
    public CharacterPageDTO toCharacterPageDTO(RickAndMortyPageDTO externalPage, int page) {
        if (externalPage == null || externalPage.getResults() == null) {
            return new CharacterPageDTO(page, Collections.emptyList());
        }

        List<CharacterDTO> characters = externalPage.getResults()
            .stream()
            .map(this::toCharacterDTO)
            .toList();

        return new CharacterPageDTO(page, characters);
    }
}

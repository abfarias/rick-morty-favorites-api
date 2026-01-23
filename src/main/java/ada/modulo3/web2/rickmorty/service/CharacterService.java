package ada.modulo3.web2.rickmorty.service;

import ada.modulo3.web2.rickmorty.client.RickAndMortyClient;
import ada.modulo3.web2.rickmorty.dto.CharacterPageDTO;
import ada.modulo3.web2.rickmorty.dto.RickAndMortyPageDTO;
import ada.modulo3.web2.rickmorty.exception.BadRequestException;
import ada.modulo3.web2.rickmorty.exception.ExternalServiceUnavailableException;
import ada.modulo3.web2.rickmorty.mapper.CharacterMapper;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Service para buscar personagens da API Rick and Morty.
 */
@ApplicationScoped
public class CharacterService {

    @Inject
    @RestClient
    RickAndMortyClient rickAndMortyClient;

    @Inject
    CharacterMapper characterMapper;

    /**
     * Busca uma página de personagens (com cache de 5 minutos).
     */
    @CacheResult(cacheName = "character-page")
    public CharacterPageDTO getCharacterPage(Integer page) {
        if (page == null || page < 1) {
            throw new BadRequestException("Página inválida");
        }

        try {
            RickAndMortyPageDTO externalPage = rickAndMortyClient.getCharacters(page);
            return characterMapper.toCharacterPageDTO(externalPage, page);

        } catch (Exception e) {
            throw new ExternalServiceUnavailableException("API Rick and Morty indisponível");
        }
    }
}

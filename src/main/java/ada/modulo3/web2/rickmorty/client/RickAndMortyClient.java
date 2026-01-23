package ada.modulo3.web2.rickmorty.client;

import ada.modulo3.web2.rickmorty.dto.RickAndMortyCharacterDTO;
import ada.modulo3.web2.rickmorty.dto.RickAndMortyPageDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Cliente REST para consumir a Rick and Morty API.
 * 
 * Configuração em application.properties:
 * - rickandmorty-api/mp-rest/url=https://rickandmortyapi.com/api
 * - rickandmorty-api/mp-rest/connectTimeout=3000
 * - rickandmorty-api/mp-rest/readTimeout=5000
 */
@RegisterRestClient(configKey = "rickandmorty-api")
@Path("/character")
public interface RickAndMortyClient {

    /**
     * Lista personagens por página.
     * 
     * @param page Número da página (começa em 1)
     * @return Página com lista de personagens
     */
    @GET
    RickAndMortyPageDTO getCharacters(@QueryParam("page") int page);

    /**
     * Busca um personagem por ID.
     * 
     * @param id ID do personagem
     * @return Dados do personagem
     */
    @GET
    @Path("/{id}")
    RickAndMortyCharacterDTO getCharacterById(@PathParam("id") long id);
}

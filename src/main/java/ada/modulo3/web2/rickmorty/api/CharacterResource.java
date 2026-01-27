package ada.modulo3.web2.rickmorty.api;

import ada.modulo3.web2.rickmorty.dto.CharacterDTO;
import ada.modulo3.web2.rickmorty.dto.CharacterPageDTO;
import ada.modulo3.web2.rickmorty.service.CharacterService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * Endpoints relacionados a personagens.
 */
@Path("/api/v1/personagens")
@Produces(MediaType.APPLICATION_JSON)
public class CharacterResource {

    @Inject
    CharacterService characterService;

    /**
     * GET /api/v1/personagens?page=1
     */
    @GET
    public CharacterPageDTO listCharacters(@QueryParam("page") Integer page) {
        Integer validPage = (page != null && page > 0) ? page : 1;
        return characterService.getCharacterPage(validPage);
    }

    /**
     * GET /api/v1/personagens/{id}
     */
    @GET
    @Path("/{id}")
    public CharacterDTO getCharacterById(@PathParam("id") Long id) {
        return characterService.getCharacterById(id);
    }
}

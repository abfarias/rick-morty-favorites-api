package ada.modulo3.web2.rickmorty.api;

import ada.modulo3.web2.rickmorty.dto.FavoriteRequestDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteResponseDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteWithCharacterDTO;
import ada.modulo3.web2.rickmorty.service.FavoriteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/personagens")
@Produces(MediaType.APPLICATION_JSON)
public class FavoriteResource {

    @Inject
    FavoriteService favoriteService;

    /**
     * GET /api/v1/personagens/favoritos
     */
    @GET
    @Path("/favoritos")
    public List<FavoriteWithCharacterDTO> getFavorites() {
        return favoriteService.getFavorites();
    }

    /**
     * POST /api/v1/personagens/favorito/{id}
     */
    @POST
    @Path("/favorito/{id}")
    public Response addFavorite(
            @PathParam("id") Long id,
            @Valid FavoriteRequestDTO request) {

        FavoriteResponseDTO response = favoriteService.addFavorite(id, request);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    /**
     * PUT /api/v1/personagens/favorito/{id}
     * Atualiza nota e comentário de um favorito.
     *
     * Fluxo:
     * - Atualiza nota e comentário no banco
     * - Não altera dados da API externa
     *
     * Retorna 200 OK com o favorito atualizado.
     */
    @PUT
    @Path("/favorito/{id}")
    public Response updateFavorite(
            @PathParam("id") Long id,
            @Valid FavoriteRequestDTO request) {

        FavoriteResponseDTO response = favoriteService.updateFavorite(id, request);

        return Response.ok(response).build();
    }

    /**
     * DELETE /api/v1/personagens/favorito/{id}
     */
    @DELETE
    @Path("/favorito/{id}")
    public Response removeFavorite(@PathParam("id") Long id) {
        favoriteService.removeFavorite(id);

        return Response.noContent().build();
    }
}

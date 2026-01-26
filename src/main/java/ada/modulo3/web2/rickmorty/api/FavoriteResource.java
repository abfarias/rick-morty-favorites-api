package ada.modulo3.web2.rickmorty.api;

import ada.modulo3.web2.rickmorty.service.FavoriteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/personagens/favorito")
public class FavoriteResource {

    @Inject
    FavoriteService favoriteService;

    /**
     * DELETE /api/v1/personagens/favorito/{id}
     * Remove o favorito do banco de dados.
     */
    @DELETE
    @Path("/{id}")
    public Response removeFavorite(@PathParam("id") Long id) {
        favoriteService.removeFavorite(id);

        // Retorna 204 No Content conforme especificado no README
        return Response.noContent().build();
    }
}
package ada.modulo3.web2.rickmorty.api;

import ada.modulo3.web2.rickmorty.dto.FavoriteRequestDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteResponseDTO;
import ada.modulo3.web2.rickmorty.service.FavoriteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/personagens/favorito")
public class FavoriteResource {

    @Inject
    FavoriteService favoriteService;

    /**
     * POST /api/v1/personagens/favorito/{id}
     * Favorita um personagem.
     *
     * Fluxo:
     * - Valida se o personagem existe na API externa (reutiliza cache)
     * - Verifica se já está favoritado
     * - Salva no banco: ID externo, nota, comentário
     *
     * Retorna 201 Created com o favorito criado.
     */
    @POST
    @Path("/{id}")
    public Response addFavorite(
            @PathParam("id") Long id,
            @Valid FavoriteRequestDTO request) {

        FavoriteResponseDTO response = favoriteService.addFavorite(id, request);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

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
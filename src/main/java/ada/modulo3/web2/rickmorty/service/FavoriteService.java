package ada.modulo3.web2.rickmorty.service;

import ada.modulo3.web2.rickmorty.client.RickAndMortyClient;
import ada.modulo3.web2.rickmorty.dto.FavoriteWithCharacterDTO;
import ada.modulo3.web2.rickmorty.dto.RickAndMortyCharacterDTO;
import ada.modulo3.web2.rickmorty.exception.NotFoundException;
import ada.modulo3.web2.rickmorty.repository.FavoriteCharacterRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class FavoriteService {

    @Inject
    FavoriteCharacterRepository favoriteRepository;
    @RestClient
    RickAndMortyClient client;


    /**
     * Remove um favorito baseado no ID do personagem (ID externo).
     * Se não encontrar registros deletados, lança NotFoundException.
     */
    @Transactional
    public void removeFavorite(Long idPersonagem) {
        // O método delete do Panache retorna a quantidade de linhas deletadas
        long deletedCount = favoriteRepository.delete("idPersonagem", idPersonagem);

        if (deletedCount == 0) {
            throw new NotFoundException("Favorito não encontrado para o ID: " + idPersonagem);
        }
    }
    public List<FavoriteWithCharacterDTO> getFavorites() {
        var favoritos = favoriteRepository.listAll();

        if (favoritos.isEmpty()) {
            return List.of();
        }

        return favoritos.stream().map(fav -> {
            RickAndMortyCharacterDTO personagem;

            try {
                personagem = client.getCharacterById(fav.getIdPersonagem());
            } catch (WebApplicationException ex) {
                int status = ex.getResponse().getStatus();

                if (status == 404) {
                    throw new WebApplicationException("Personagem não encontrado", Response.Status.NOT_FOUND);
                }

                if (status >= 500) {
                    throw new WebApplicationException("API externa indisponível", Response.Status.SERVICE_UNAVAILABLE);
                }

                throw new WebApplicationException("Erro inesperado", Response.Status.INTERNAL_SERVER_ERROR);
            }

            return new FavoriteWithCharacterDTO(
                    fav.getIdPersonagem(),
                    fav.getNota(),
                    fav.getComentario(),
                    personagem.getName(),
                    personagem.getStatus()
            );
        }).collect(Collectors.toList());
    }







}
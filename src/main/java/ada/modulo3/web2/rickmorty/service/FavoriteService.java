package ada.modulo3.web2.rickmorty.service;

import ada.modulo3.web2.rickmorty.exception.NotFoundException;
import ada.modulo3.web2.rickmorty.repository.FavoriteCharacterRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FavoriteService {

    @Inject
    FavoriteCharacterRepository favoriteRepository;

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
}
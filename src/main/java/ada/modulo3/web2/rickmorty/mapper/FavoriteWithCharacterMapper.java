package ada.modulo3.web2.rickmorty.mapper;

import ada.modulo3.web2.rickmorty.dto.CharacterDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteWithCharacterDTO;
import ada.modulo3.web2.rickmorty.entity.FavoriteCharacter;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper para agregar dados de favoritos (banco) com dados externos (API).
 */
@ApplicationScoped
public class FavoriteWithCharacterMapper {

    /**
     * Agrega FavoriteCharacter + CharacterDTO → FavoriteWithCharacterDTO
     */
    public FavoriteWithCharacterDTO toDTO(FavoriteCharacter favorite, CharacterDTO character) {
        if (favorite == null) {
            return null;
        }

        String name = character != null ? character.getName() : null;
        String status = character != null ? character.getStatus() : null;

        return new FavoriteWithCharacterDTO(
            favorite.getIdPersonagem(),
            favorite.getNota(),
            favorite.getComentario(),
            name,
            status
        );
    }
}

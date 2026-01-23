package ada.modulo3.web2.rickmorty.mapper;

import ada.modulo3.web2.rickmorty.dto.FavoriteRequestDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteResponseDTO;
import ada.modulo3.web2.rickmorty.entity.FavoriteCharacter;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper para conversões entre FavoriteCharacter e DTOs.
 */
@ApplicationScoped
public class FavoriteMapper {

    /**
     * Converte Entity → ResponseDTO
     */
    public FavoriteResponseDTO toResponseDTO(FavoriteCharacter entity) {
        if (entity == null) {
            return null;
        }
        return new FavoriteResponseDTO(
            entity.getIdPersonagem(),
            entity.getNota(),
            entity.getComentario()
        );
    }

    /**
     * Converte RequestDTO + idPersonagem → Entity (para criar a entidade a ser persistida no banco)
     */
    public FavoriteCharacter toEntity(Long idPersonagem, FavoriteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new FavoriteCharacter(
            idPersonagem,
            dto.getNota(),
            dto.getComentario()
        );
    }

    /**
     * Atualiza uma entidade existente com dados do RequestDTO
     */
    public void updateEntityFromDTO(FavoriteCharacter entity, FavoriteRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setNota(dto.getNota());
        entity.setComentario(dto.getComentario());
    }
}

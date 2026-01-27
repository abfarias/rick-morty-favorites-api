package ada.modulo3.web2.rickmorty.service;

import ada.modulo3.web2.rickmorty.dto.FavoriteRequestDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteResponseDTO;
import ada.modulo3.web2.rickmorty.entity.FavoriteCharacter;
import ada.modulo3.web2.rickmorty.exception.ConflictException;
import ada.modulo3.web2.rickmorty.exception.NotFoundException;
import ada.modulo3.web2.rickmorty.mapper.FavoriteMapper;
import ada.modulo3.web2.rickmorty.repository.FavoriteCharacterRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FavoriteService {

    @Inject
    FavoriteCharacterRepository favoriteRepository;

    @Inject
    CharacterService characterService;

    @Inject
    FavoriteMapper favoriteMapper;

    /**
     * Favorita um personagem.
     *
     * Fluxo:
     * 1. Verifica se o personagem existe na API externa (reutiliza cache)
     * 2. Verifica se já está favoritado (409 Conflict)
     * 3. Salva no banco de dados
     * 4. Retorna o favorito criado
     */
    @Transactional
    public FavoriteResponseDTO addFavorite(Long idPersonagem, FavoriteRequestDTO request) {
        // 1. Valida se o personagem existe na API externa (reutiliza cache do endpoint 2)
        // Se não existir, CharacterService lança NotFoundException
        // Se API indisponível, lança ExternalServiceUnavailableException
        characterService.getCharacterById(idPersonagem);

        // 2. Verifica se já está favoritado
        if (favoriteRepository.existsByIdPersonagem(idPersonagem)) {
            throw new ConflictException("Personagem já favoritado: " + idPersonagem);
        }

        // 3. Cria e persiste a entidade
        FavoriteCharacter entity = favoriteMapper.toEntity(idPersonagem, request);
        favoriteRepository.persist(entity);

        // 4. Retorna o DTO de resposta
        return favoriteMapper.toResponseDTO(entity);
    }

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
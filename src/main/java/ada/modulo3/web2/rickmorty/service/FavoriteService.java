package ada.modulo3.web2.rickmorty.service;

import ada.modulo3.web2.rickmorty.dto.CharacterDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteRequestDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteResponseDTO;
import ada.modulo3.web2.rickmorty.dto.FavoriteWithCharacterDTO;
import ada.modulo3.web2.rickmorty.entity.FavoriteCharacter;
import ada.modulo3.web2.rickmorty.exception.ConflictException;
import ada.modulo3.web2.rickmorty.exception.ExternalServiceUnavailableException;
import ada.modulo3.web2.rickmorty.exception.NotFoundException;
import ada.modulo3.web2.rickmorty.mapper.FavoriteMapper;
import ada.modulo3.web2.rickmorty.mapper.FavoriteWithCharacterMapper;
import ada.modulo3.web2.rickmorty.repository.FavoriteCharacterRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class FavoriteService {

    @Inject
    FavoriteCharacterRepository favoriteRepository;

    @Inject
    CharacterService characterService;

    @Inject
    FavoriteMapper favoriteMapper;

    @Inject FavoriteWithCharacterMapper favoriteWithCharacterMapper;

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
     * Lista todos os personagens favoritos com dados agregados.
     *
     * Estratégia de cache (conforme README):
     * - Para cada favorito: busca dados do personagem no cache
     * - Só chama a API pública se não estiver cacheado
     *
     * Fluxo:
     * - Busca os favoritos no banco de dados
     * - Para cada favorito: busca dados externos via CharacterService (com cache)
     * - Agrega dados usando FavoriteWithCharacterMapper
     * - Retorna lista final (ou lista vazia se não houver favoritos)
     */
    public List<FavoriteWithCharacterDTO> getFavorites() {
        List<FavoriteCharacter> favoritos = favoriteRepository.listAll();

        if (favoritos.isEmpty()) {
            return List.of();
        }

        return favoritos.stream()
            .map(favorite -> {
                try {
                    // Busca dados do personagem no cache
                    CharacterDTO character = characterService.getCharacterById(favorite.getIdPersonagem());

                    // Agrega usando o mapper
                    return favoriteWithCharacterMapper.toDTO(favorite, character);

                } catch (NotFoundException e) {
                    // Personagem foi deletado da API externa
                    // Retorna favorito sem dados externos (name/status = null)
                    return favoriteWithCharacterMapper.toDTO(favorite, null);

                } catch (ExternalServiceUnavailableException e) {
                    // API externa indisponível - propaga a exceção para retornar 503
                    throw e;

                } catch (Exception e) {
                    // Qualquer outro erro não previsto
                    // Retorna favorito sem dados externos para não quebrar toda a lista
                    return favoriteWithCharacterMapper.toDTO(favorite, null);
                }
            })
            .collect(Collectors.toList());
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
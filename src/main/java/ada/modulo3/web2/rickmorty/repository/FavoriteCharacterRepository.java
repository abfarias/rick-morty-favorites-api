package ada.modulo3.web2.rickmorty.repository;

import ada.modulo3.web2.rickmorty.entity.FavoriteCharacter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class FavoriteCharacterRepository implements PanacheRepository<FavoriteCharacter> {

    public Optional<FavoriteCharacter> findByIdPersonagem(long idPersonagem) {
        return find("idPersonagem", idPersonagem).firstResultOptional();
    }

    public boolean existsByIdPersonagem(long idPersonagem) {
        return count("idPersonagem", idPersonagem) > 0;
    }
}

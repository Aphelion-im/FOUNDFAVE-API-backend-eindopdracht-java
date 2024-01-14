package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findByCharacterAliasNameIgnoreCase (String name);
    List<Character> findByCharacterAliasNameStartingWithIgnoreCase(String name);
    List<Character> findByCharacterAliasNameContainsIgnoreCase(String name);
    List<Character> findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameAsc(String name);
    List<Character> findByCharacterAliasNameStartingWithIgnoreCaseOrderByCharacterAliasNameDesc(String name);
}


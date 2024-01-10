package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
}


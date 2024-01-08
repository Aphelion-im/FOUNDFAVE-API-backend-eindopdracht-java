package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}


package spring.app.testPlayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.app.testPlayer.model.Music;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    Music findByName(String name);
}

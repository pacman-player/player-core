package spring.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.app.model.GoogleUsers;

import java.util.Optional;

public interface UserDetailsRepoGoogle extends JpaRepository<GoogleUsers, String> {

    Optional<GoogleUsers> findById(String id);

}

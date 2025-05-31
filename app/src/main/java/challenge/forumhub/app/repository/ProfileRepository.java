package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.Profile;
import challenge.forumhub.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByRoleAndActiveTrue(Role role);
}

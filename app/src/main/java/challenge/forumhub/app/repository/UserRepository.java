package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

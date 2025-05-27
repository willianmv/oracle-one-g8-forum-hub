package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByActiveTrue();

    User findByIdAndActiveTrue(long id);
}

package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTitleIgnoreCaseAndMessageIgnoreCase(String title, String message);

    List<Topic> findAllByActiveTrue();

    Optional<Topic> findByIdAndActiveTrue(long id);

    boolean existsByTitleIgnoreCaseAndMessageIgnoreCaseAndIdNot(String title, String message, long id);

}

package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    boolean existsByTitleIgnoreCaseAndSolutionIgnoreCase(String title, String solution);

    List<Reply> findAllByActiveTrue();

    Optional<Reply> findByIdAndActiveTrue(long id);

    boolean existsByTitleIgnoreCaseAndSolutionIgnoreCaseAndIdNot(String title, String solution, long id);
}

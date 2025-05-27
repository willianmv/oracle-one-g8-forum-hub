package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.Topic;
import challenge.forumhub.app.entity.TopicStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTitleIgnoreCaseAndMessageIgnoreCase(String title, String message);

    List<Topic> findAllByActiveTrue();

    Optional<Topic> findByIdAndActiveTrue(long id);

    boolean existsByTitleIgnoreCaseAndMessageIgnoreCaseAndIdNot(String title, String message, long id);

    @Query("""
            SELECT DISTINCT t FROM Topic t
            JOIN t.course c
            JOIN c.categories cat
            WHERE (:authorId IS NULL OR t.author.id = :authorId)
              AND (:courseId IS NULL OR c.id = :courseId)
              AND (:categoryId IS NULL OR cat.id = :categoryId)
              AND (:status IS NULL OR t.status = :status)
              AND t.active = true
            """)
    List<Topic> findByFilters(
            @Param("authorId") Long authorId,
            @Param("courseId") Long courseId,
            @Param("categoryId") Long categoryId,
            @Param("status")TopicStatus status);
}

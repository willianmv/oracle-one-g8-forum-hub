package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByNameIgnoreCase(String name);

    List<Course> findAllByActiveTrue();

    Optional<Course> findByIdAndActiveTrue(long id);

    boolean existsByNameIgnoreCaseAndIdNot(String name, long id);
}

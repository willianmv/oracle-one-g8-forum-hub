package challenge.forumhub.app.repository;

import challenge.forumhub.app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);

    List<Category> findAllByActiveTrue();

    Set<Category> findAllByIdInAndActiveTrue(Set<Long> ids);

    Optional <Category> findByIdAndActiveTrue(long id);

    boolean existsByNameIgnoreCaseAndIdNot(String name, long id);
}

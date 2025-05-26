package challenge.forumhub.app.mapper;

import challenge.forumhub.app.dto.category.CategoryDetailsDTO;
import challenge.forumhub.app.dto.category.CategorySummaryDTO;
import challenge.forumhub.app.entity.Category;
import challenge.forumhub.app.entity.Course;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryDetailsDTO toDetailsDTO(Category categorySaved) {
        Set<String> coursesNames = categorySaved.getCourses() == null
                ? Set.of()
                : categorySaved.getCourses().stream()
                .map(Course::getName)
                .collect(Collectors.toSet());

        String userCreator = categorySaved.getCreatedBy() == null
                ? "Desconhecido"
                : categorySaved.getCreatedBy().getName();

        return new CategoryDetailsDTO(
                categorySaved.getId(), categorySaved.getName(),
                userCreator, categorySaved.getCreatedAt(),
                categorySaved.getModifiedAt(), coursesNames);
    }

    public CategorySummaryDTO toSummaryDTO(Category category){
        int courseCount = category.getCourses() != null
                ? category.getCourses().size()
                : 0;
        return new CategorySummaryDTO(category.getId(), category.getName(), courseCount);
    }

}

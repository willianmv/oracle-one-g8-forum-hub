package challenge.forumhub.app.mapper;

import challenge.forumhub.app.dto.course.CourseDetailsDTO;
import challenge.forumhub.app.dto.course.CourseSummaryDTO;
import challenge.forumhub.app.entity.Category;
import challenge.forumhub.app.entity.Course;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public CourseDetailsDTO toDetailsDTO(Course course){
        Set<String> categories = course.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        return new CourseDetailsDTO(
                course.getId(), course.getName(), course.getCreatedBy().getName(),
                course.getCreatedAt(), course.getModifiedAt(), categories);
    }

    public CourseSummaryDTO toSummaryDTO(Course course) {
        int categoryCount = course.getCategories().size();
        return new CourseSummaryDTO(course.getId(), course.getName(), categoryCount);
    }
}

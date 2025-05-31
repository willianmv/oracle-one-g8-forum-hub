package challenge.forumhub.app.service;

import challenge.forumhub.app.dto.course.CourseRequestDTO;
import challenge.forumhub.app.dto.course.CourseUpdateDTO;
import challenge.forumhub.app.entity.Category;
import challenge.forumhub.app.entity.Course;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.exception.RelationValidationException;
import challenge.forumhub.app.exception.ResourceAlreadyExistsException;
import challenge.forumhub.app.exception.ResourceNotFoundException;
import challenge.forumhub.app.repository.CourseRepository;
import challenge.forumhub.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;


    @Transactional
    public Course create(CourseRequestDTO dto) {
        User user = authenticatedUserService.getAuthenticatedUserEntity();
        validateCourseNameToCreate(dto);
        Set<Category> categories = validateCategories(dto.categoryIds());
        Course course = new Course();
        course.setName(dto.name());
        course.setCreatedBy(user);
        course.setCategories(categories);
        return courseRepository.save(course);
    }

    public List<Course> getAll(){
        return courseRepository.findAllByActiveTrue();
    }

    public Course getCourseById(long id) {
        return courseRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com ID: "+id));
    }

    @Transactional
    public Course updateCourse(long id, CourseUpdateDTO dto) {
        Course courseToUpdate = getCourseById(id);
        validateCourseNameToUpdate(dto, id);
        Set<Category> categories = validateCategories(dto.categoryIds());
        courseToUpdate.setName(dto.name());
        courseToUpdate.setCategories(categories);
        return courseRepository.save(courseToUpdate);
    }

    @Transactional
    public void deleteCourse(long id) {
        Course course = getCourseById(id);
        course.setActive(false);
        courseRepository.save(course);
    }

    private void validateCourseNameToCreate(CourseRequestDTO dto){
        if(courseRepository.existsByNameIgnoreCase(dto.name()))
            throw new ResourceAlreadyExistsException("Curso já registrado com o nome: "+dto.name());
    }

    private void validateCourseNameToUpdate(CourseUpdateDTO dto, long id){
        if(courseRepository.existsByNameIgnoreCaseAndIdNot(dto.name(), id)){
            throw new ResourceAlreadyExistsException("Existe um curso com outro ID já registrado com o nome: "+dto.name());
        }
    }

    private Set<Category> validateCategories(Set<Long> categoryIds) {
        Set<Category> categories = categoryService.findAllByIds(categoryIds);
        if(categories.isEmpty())
            throw new RelationValidationException("Pelo menos uma categoria válida deve ser informada");

        if(categories.size() < categoryIds.size()){
            Set<Long> foundIds = categories.stream().map(Category::getId).collect(Collectors.toSet());
            Set<Long> missingIds = new HashSet<>(categoryIds);
            missingIds.removeAll(foundIds);
            throw new RelationValidationException("Os seguintes IDs não foram encontrados: "+missingIds);
        }
        return categories;
    }
}

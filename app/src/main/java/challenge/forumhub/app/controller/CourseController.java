package challenge.forumhub.app.controller;

import challenge.forumhub.app.controller.doc.CourseControllerDoc;
import challenge.forumhub.app.dto.course.CourseDetailsDTO;
import challenge.forumhub.app.dto.course.CourseRequestDTO;
import challenge.forumhub.app.dto.course.CourseSummaryDTO;
import challenge.forumhub.app.dto.course.CourseUpdateDTO;
import challenge.forumhub.app.entity.Course;
import challenge.forumhub.app.mapper.CourseMapper;
import challenge.forumhub.app.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController implements CourseControllerDoc {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDetailsDTO> create(@RequestBody @Valid CourseRequestDTO dto){
        Course createdCourse = courseService.create(dto);
        CourseDetailsDTO responseDTO = courseMapper.toDetailsDTO(createdCourse);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCourse.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CourseSummaryDTO>> getAllCourses(){
        List<CourseSummaryDTO> courses = courseService.getAll()
                .stream()
                .map(courseMapper::toSummaryDTO)
                .sorted(Comparator.comparing(CourseSummaryDTO::id))
                .toList();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailsDTO> getCourseById(@PathVariable("courseId") long id){
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(courseMapper.toDetailsDTO(course));
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDetailsDTO> updateCourse(@PathVariable("courseId") long id,
                                                         @RequestBody @Valid CourseUpdateDTO dto){
        Course updatedCourse = courseService.updateCourse(id, dto);
        return ResponseEntity.ok(courseMapper.toDetailsDTO(updatedCourse));
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

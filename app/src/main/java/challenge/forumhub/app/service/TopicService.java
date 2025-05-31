package challenge.forumhub.app.service;

import challenge.forumhub.app.dto.topic.TopicFilterParams;
import challenge.forumhub.app.dto.topic.TopicRequestDTO;
import challenge.forumhub.app.dto.topic.TopicUpdateDTO;
import challenge.forumhub.app.entity.Course;
import challenge.forumhub.app.entity.Topic;
import challenge.forumhub.app.entity.TopicStatus;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.exception.ResourceAlreadyExistsException;
import challenge.forumhub.app.exception.ResourceNotFoundException;
import challenge.forumhub.app.repository.TopicRepository;
import challenge.forumhub.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final CourseService courseService;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Transactional
    public Topic create(TopicRequestDTO dto) {
        User user = authenticatedUserService.getAuthenticatedUserEntity();
        Course course = validateCourse(dto.courseId());
        validateDuplicateToCreateTopic(dto.title(), dto.message());
        Topic topic = new Topic();
        topic.setTitle(dto.title());
        topic.setMessage(dto.message());
        topic.setAuthor(user);
        topic.setCourse(course);
        topic.setStatus(TopicStatus.NO_REPLIES);
        return topicRepository.save(topic);
    }

    public Topic getTopicById(long id) {
        return topicRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico não encontrado com ID: "+id));
    }

    public List<Topic> getAll() {
        return topicRepository.findAllByActiveTrue();
    }

    public List<Topic> getTopicsByFilters(TopicFilterParams params){
        return topicRepository.findByFilters(
                params.authorId(),
                params.courseId(),
                params.categoryId(),
                params.status());
    }

    @Transactional
    public Topic updateTopic(long id, TopicUpdateDTO dto) {
        Topic topicToUpdate = getTopicById(id);

        authenticatedUserService.verifyOwnerOrModeratorOrAdmin(topicToUpdate.getAuthor().getId());

        validateDuplicateToUpdateTopic(dto.title(), dto.message(), id);
        Course course = validateCourse(dto.courseId());
        topicToUpdate.setTitle(dto.title());
        topicToUpdate.setMessage(dto.message());
        topicToUpdate.setCourse(course);
        return topicRepository.save(topicToUpdate);
    }

    @Transactional
    public void deleteTopic(long id) {
        Topic topic = getTopicById(id);
        authenticatedUserService.verifyOwnerOrModeratorOrAdmin(topic.getAuthor().getId());
        topic.setActive(false);
        topicRepository.save(topic);
    }

    private void validateDuplicateToCreateTopic(String title, String message) {
        if(topicRepository.existsByTitleIgnoreCaseAndMessageIgnoreCase(title, message))
            throw new ResourceAlreadyExistsException("Já existe um tópico com o mesmo título e mensagem");
    }

    private void validateDuplicateToUpdateTopic(String title, String message, long id){
        if(topicRepository.existsByTitleIgnoreCaseAndMessageIgnoreCaseAndIdNot(title, message, id))
            throw new ResourceAlreadyExistsException("Já existe um tópico com o mesmo título e mensagem");
    }

    private Course validateCourse(long courseId) {
        return courseService.getCourseById(courseId);
    }
}

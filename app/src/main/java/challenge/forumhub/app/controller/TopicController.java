package challenge.forumhub.app.controller;

import challenge.forumhub.app.controller.doc.TopicControllerDoc;
import challenge.forumhub.app.dto.topic.*;
import challenge.forumhub.app.entity.Topic;
import challenge.forumhub.app.entity.TopicStatus;
import challenge.forumhub.app.mapper.TopicMapper;
import challenge.forumhub.app.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController implements TopicControllerDoc {

    private final TopicService topicService;
    private final TopicMapper topicMapper;

    @PostMapping
    public ResponseEntity<TopicDetailsDTO> create(@RequestBody @Valid TopicRequestDTO dto){
        Topic createdTopic = topicService.create(dto);
        TopicDetailsDTO responseDTO = topicMapper.toDetailsDTO(createdTopic);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTopic.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TopicSummaryDTO>> getAllTopics(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) TopicStatus status)
    {
        TopicFilterParams params = new TopicFilterParams(authorId, courseId, categoryId, status);

        List<TopicSummaryDTO> topics = topicService.getTopicsByFilters(params)
                .stream()
                .map(topicMapper::toSummaryDTO)
                .sorted(Comparator.comparing(TopicSummaryDTO::id))
                .toList();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicDetailsDTO> getTopicById(@PathVariable("topicId") long id){
        Topic topic = topicService.getTopicById(id);
        return ResponseEntity.ok(topicMapper.toDetailsDTO(topic));
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<TopicDetailsDTO> updateTopic(@PathVariable("topicId") long id,
                                                        @RequestBody @Valid TopicUpdateDTO dto){
        Topic updatedTopic = topicService.updateTopic(id, dto);
        return ResponseEntity.ok(topicMapper.toDetailsDTO(updatedTopic));
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("topicId") long id){
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

}

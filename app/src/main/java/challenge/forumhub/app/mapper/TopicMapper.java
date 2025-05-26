package challenge.forumhub.app.mapper;

import challenge.forumhub.app.dto.topic.TopicDetailsDTO;
import challenge.forumhub.app.dto.topic.TopicSummaryDTO;
import challenge.forumhub.app.entity.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public TopicDetailsDTO toDetailsDTO(Topic topic){

        return new TopicDetailsDTO(
                topic.getId(),
                topic.getCourse().getName(),
                topic.getAuthor().getName(),
                topic.getAuthor().getEmail(),
                topic.getCreatedAt(),
                topic.getModifiedAt(),
                topic.getTitle(),
                topic.getMessage(),
                null);
    }

    public TopicSummaryDTO toSummaryDTO(Topic topic){
        int replyCount = topic.getReplies() == null
                ? 0
                : topic.getReplies().size();

        return new TopicSummaryDTO(
                topic.getId(),
                topic.getCourse().getName(),
                topic.getAuthor().getEmail(),
                topic.getTitle(), topic.getStatus().name(),
                replyCount);
    }

}

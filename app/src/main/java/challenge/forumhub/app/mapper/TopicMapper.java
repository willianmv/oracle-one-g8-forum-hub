package challenge.forumhub.app.mapper;

import challenge.forumhub.app.dto.reply.ReplyDetailsDTO;
import challenge.forumhub.app.dto.reply.ReplyTopicDTo;
import challenge.forumhub.app.dto.topic.TopicDetailsDTO;
import challenge.forumhub.app.dto.topic.TopicSummaryDTO;
import challenge.forumhub.app.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicMapper {

    private final ReplyMapper replyMapper;

    public TopicDetailsDTO toDetailsDTO(Topic topic){

        List<ReplyTopicDTo> replies = topic.getReplies() == null
                ? null
                : topic.getReplies().stream().map(replyMapper::toReplyTopicDTO).toList();

        return new TopicDetailsDTO(
                topic.getId(),
                topic.getCourse().getName(),
                topic.getAuthor().getName(),
                topic.getAuthor().getEmail(),
                topic.getCreatedAt(),
                topic.getModifiedAt(),
                topic.getTitle(),
                topic.getMessage(),
                replies);
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

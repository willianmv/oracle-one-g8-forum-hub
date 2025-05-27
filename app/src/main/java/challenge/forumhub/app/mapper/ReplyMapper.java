package challenge.forumhub.app.mapper;

import challenge.forumhub.app.dto.reply.ReplyDetailsDTO;
import challenge.forumhub.app.dto.reply.ReplySummaryDTO;
import challenge.forumhub.app.dto.reply.ReplyTopicDTo;
import challenge.forumhub.app.entity.Reply;
import org.springframework.stereotype.Component;

@Component
public class ReplyMapper {

    public ReplyDetailsDTO toDetailsDTO(Reply reply) {
        return new ReplyDetailsDTO(
                reply.getId(),
                reply.getTopic().getTitle(),
                reply.getAuthor().getName(),
                reply.getAuthor().getEmail(),
                reply.getCreatedAt(), reply.getModifiedAt(),
                reply.getTitle(), reply.getSolution());
    }

    public ReplySummaryDTO toSummaryDTO(Reply reply) {
        return new ReplySummaryDTO(
                reply.getAuthor().getEmail(),
                reply.getTopic().getId(), reply.getTopic().getTitle(),
                reply.getId(), reply.getTitle());
    }

    public ReplyTopicDTo toReplyTopicDTO(Reply reply){
        return new ReplyTopicDTo(
                reply.getCreatedAt(),
                reply.getAuthor().getEmail(),
                reply.getTitle(), reply.getSolution());
    }
}

package challenge.forumhub.app.dto.topic;

import challenge.forumhub.app.dto.reply.ReplyTopicDTo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record TopicDetailsDTO(
        long id,
        String course,
        String authorName,
        String authorEmail,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime modifiedAt,
        String title,
        String message,
        List<ReplyTopicDTo> replies
) {}

package challenge.forumhub.app.dto.reply;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReplyTopicDTo(
        long id,
        String authorName,
        String authorEmail,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime createdAt,
        String title,
        String solution
) {}

package challenge.forumhub.app.dto.reply;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReplyTopicDTo(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime createdAt,
        String authorEmail,
        String title,
        String solution
) {}

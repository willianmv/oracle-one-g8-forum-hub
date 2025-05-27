package challenge.forumhub.app.dto.reply;

public record ReplySummaryDTO(
        long id,
        String topicTitle,
        String authorEmail,
        String replyTitle
) {}

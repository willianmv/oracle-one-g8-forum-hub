package challenge.forumhub.app.dto.reply;

public record ReplySummaryDTO(
        String authorEmail,
        long topicId,
        String topicTitle,
        long replyId,
        String replyTitle
) {}

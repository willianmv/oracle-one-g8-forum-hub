package challenge.forumhub.app.dto.topic;

public record TopicSummaryDTO(
        long id,
        String course,
        String authorEmail,
        String title,
        String status,
        int replyCount
) {}

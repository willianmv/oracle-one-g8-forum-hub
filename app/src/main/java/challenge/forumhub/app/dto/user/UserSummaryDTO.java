package challenge.forumhub.app.dto.user;

public record UserSummaryDTO(
        long id,
        String email,
        int topicCount,
        int replyCount
) {}

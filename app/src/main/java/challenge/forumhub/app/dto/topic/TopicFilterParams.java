package challenge.forumhub.app.dto.topic;

import challenge.forumhub.app.entity.TopicStatus;

public record TopicFilterParams(
        Long authorId,
        Long courseId,
        Long categoryId,
        TopicStatus status
) {}

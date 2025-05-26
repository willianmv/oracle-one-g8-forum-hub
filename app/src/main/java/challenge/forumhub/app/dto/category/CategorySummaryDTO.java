package challenge.forumhub.app.dto.category;

public record CategorySummaryDTO(
        long id,
        String name,
        int courseCount
) {}

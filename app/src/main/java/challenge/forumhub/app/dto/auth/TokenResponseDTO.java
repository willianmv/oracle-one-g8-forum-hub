package challenge.forumhub.app.dto.auth;

public record TokenResponseDTO(
        String token,
        long expiresIn
) {}

package challenge.forumhub.app.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record UserDetailsDTO(
        long id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime createdAt,
        String email,
        String name,
        Set<String> hasTopicInCourses,
        Set<String> topicTitles,
        Set<String> replyTitles
) {}

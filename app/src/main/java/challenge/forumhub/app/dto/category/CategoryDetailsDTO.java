package challenge.forumhub.app.dto.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record CategoryDetailsDTO(
        long id,
        String name,
        String createdBy,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime modifiedAt,
        Set<String> courses
) {}

package challenge.forumhub.app.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime timeStamp,
        String path,
        int status,
        String error,
        String errorCode,
        String message,
        List<FieldErrorDTO> fieldErrors
) {
    public static record FieldErrorDTO(String field, String message){}
}

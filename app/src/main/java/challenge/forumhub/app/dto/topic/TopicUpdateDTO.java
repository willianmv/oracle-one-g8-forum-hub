package challenge.forumhub.app.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopicUpdateDTO(
        @NotNull(message = "O campo ID do curso é obrigatório")
        Long courseId,

        @NotBlank(message = "Campo nome é obrigatório")
        @Size(min = 3, max = 50, message = "O campo nome deve ter entre {min} e {max} caracteres")
        String title,

        @NotBlank(message = "Campo mensagem é obrigatório")
        @Size(min = 10, max = 10000, message = "O campo mensagem deve ter entre {min} e {max} caracteres")
        String message
) {}

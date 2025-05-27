package challenge.forumhub.app.dto.reply;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReplyUpdateDTO(
        @NotNull(message = "O campo ID do tópico é obrigatório")
        Long topicId,

        @NotBlank(message = "Campo título é obrigatório")
        @Size(min = 3, max = 50, message = "O campo nome deve ter entre {min} e {max} caracteres")
        String title,

        @NotBlank(message = "Campo solução é obrigatório")
        @Size(min = 10, max = 10000, message = "O campo solução deve ter entre {min} e {max} caracteres")
        String solution
) {}

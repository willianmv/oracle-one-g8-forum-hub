package challenge.forumhub.app.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateDTO(
        @NotBlank(message = "Campo nome é obrigatório")
        @Size(min = 3, max = 50, message = "O campo nome deve ter entre {min} e {max} caracteres")
        String name
) {}

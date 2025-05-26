package challenge.forumhub.app.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CourseRequestDTO(
        @NotBlank(message = "Campo nome é obrigatório")
        @Size(min = 3, max = 50, message = "O campo nome deve ter entre {min} e {max} caracteres")
        String name,

        @NotEmpty(message = "Pelo menos o ID de uma categoria deve ser informado")
        Set<Long> categoryIds
) {}

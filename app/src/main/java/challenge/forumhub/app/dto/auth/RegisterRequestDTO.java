package challenge.forumhub.app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotEmpty(message = "Campo nome é obrigatório")
        @NotBlank(message = "Campo nome é obrigatório")
        String name,

        @NotEmpty(message = "Campo e-mail é obrigatório")
        @NotBlank(message = "Campo e-mail é obrigatório")
        @Email(message = "E-mail mal formatado")
        String email,

        @NotEmpty(message = "Campo senha é obrigatório")
        @NotBlank(message = "Campo senha é obrigatório")
        @Size(min = 8, message = "Senha deve ter 8 no mínimo 8 carateres")
        String password
) {}

package challenge.forumhub.app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDTO(
        @NotEmpty(message = "Campo e-mail é obrigatório")
        @NotBlank(message = "Campo e-mail é obrigatório")
        @Email(message = "E-mail mal formatado")
        String email,

        @NotEmpty(message = "Campo senha é obrigatório")
        @NotBlank(message = "Campo senha é obrigatório")
        String password
) {}

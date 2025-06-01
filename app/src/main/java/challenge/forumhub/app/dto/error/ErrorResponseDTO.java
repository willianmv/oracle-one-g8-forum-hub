package challenge.forumhub.app.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        @Schema(description = "Data e hora do erro")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime timeStamp,
        @Schema(description = "Rota acessada que retornou o erro")
        String path,
        @Schema(description = "Código HTTP do erro")
        int status,
        @Schema(description = "Mensagem de erro padrão HTTP")
        String error,
        @Schema(description = "Código de erro padronizado da API")
        String errorCode,
        @Schema(description = "Mensagem de erro")
        String message,
        @Schema(description = "Campos de erros de validação")
        List<FieldErrorDTO> fieldErrors
) {
    public static record FieldErrorDTO(String field, String message){}
}

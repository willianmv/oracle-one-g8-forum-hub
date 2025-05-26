package challenge.forumhub.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //Relacionado a regras de negócio
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "Recurso não encontrado"),
    RESOURCE_ALREADY_EXISTS("RESOURCE_ALREADY_EXISTS", HttpStatus.BAD_REQUEST, "Recurso já existe"),
    RESOURCE_INACTIVE("RESOURCE_INACTIVE", HttpStatus.BAD_REQUEST, "Recurso está inativo"),

    //Relacionado a validação de campos
    VALIDATION_ERROR("VALIDATION_ERROR", HttpStatus.BAD_REQUEST, "Erro de validação nos campos"),

    //Relacionado a erros genéricos
    INTERNAL_ERROR("INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");

    private final String code;
    private final HttpStatus httpStatus;
    private final String defaultMessage;

    ErrorCode(String code, HttpStatus httpStatus, String defaultMessage) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

}

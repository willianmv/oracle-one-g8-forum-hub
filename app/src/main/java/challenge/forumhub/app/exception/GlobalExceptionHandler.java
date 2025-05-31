package challenge.forumhub.app.exception;

import challenge.forumhub.app.dto.error.ErrorResponseDTO;
import challenge.forumhub.app.dto.error.ErrorResponseDTO.FieldErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex, HttpServletRequest request){
        var code = ex.getErrorCode();
        return buildErrorResponse(code.getHttpStatus(), code.getCode(), ex.getMessage(), null, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        var code = ErrorCode.VALIDATION_ERROR;
        List<FieldErrorDTO> fieldErrors = ex.getFieldErrors().stream()
                .map(err -> new FieldErrorDTO(err.getField(), err.getDefaultMessage())).toList();
        return buildErrorResponse(code.getHttpStatus(), code.getCode(), code.getDefaultMessage(), fieldErrors, request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request){
        var code = ErrorCode.BAD_CREDENTIALS;
        return buildErrorResponse(code.getHttpStatus(), code.getCode(), code.getDefaultMessage(), null, request.getRequestURI());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(AuthorizationDeniedException ex, HttpServletRequest request){
        var code = ErrorCode.ACCESS_DENIED;
        return buildErrorResponse(code.getHttpStatus(), code.getCode(), ex.getMessage(), null, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedExceptions(Exception ex, HttpServletRequest request){
        var code = ErrorCode.INTERNAL_ERROR;
        log.error("Unexpected Error: ", ex);
        return buildErrorResponse(code.getHttpStatus(), code.getCode(),
                code.getDefaultMessage() + ": "+ex.getMessage(),
                null, request.getRequestURI());
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            HttpStatus httpStatus, String errorCode, String message, List<FieldErrorDTO> fieldErrors, String path)
    {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                path, httpStatus.value(),
                httpStatus.getReasonPhrase(),
                errorCode, message, fieldErrors);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}

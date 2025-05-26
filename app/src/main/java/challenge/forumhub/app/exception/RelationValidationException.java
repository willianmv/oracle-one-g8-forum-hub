package challenge.forumhub.app.exception;

public class RelationValidationException extends BusinessException{

    public RelationValidationException() {
        super(ErrorCode.RELATION_VALIDATION_ERROR);
    }

    public RelationValidationException(String customMessage) {
        super(ErrorCode.RELATION_VALIDATION_ERROR, customMessage);
    }
}

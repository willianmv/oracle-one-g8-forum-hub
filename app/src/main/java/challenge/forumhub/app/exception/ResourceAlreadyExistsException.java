package challenge.forumhub.app.exception;

public class ResourceAlreadyExistsException extends BusinessException{

    public ResourceAlreadyExistsException() {
        super(ErrorCode.RESOURCE_ALREADY_EXISTS);
    }

    public ResourceAlreadyExistsException(String customMessage) {
        super(ErrorCode.RESOURCE_ALREADY_EXISTS, customMessage);
    }
}

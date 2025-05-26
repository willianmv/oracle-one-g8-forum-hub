package challenge.forumhub.app.exception;

public class ResourceNotFoundException extends BusinessException{

    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(String customMessage) {
        super(ErrorCode.RESOURCE_NOT_FOUND, customMessage);
    }
}

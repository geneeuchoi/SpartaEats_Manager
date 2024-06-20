package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class ManagerException extends CustomException{
    public ManagerException(ErrorType errorType) {
        super(errorType);
    }
}

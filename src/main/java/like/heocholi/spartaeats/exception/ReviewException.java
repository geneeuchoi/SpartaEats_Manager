package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class ReviewException extends CustomException {
    public ReviewException(ErrorType errorType) {
        super(errorType);
    }
}

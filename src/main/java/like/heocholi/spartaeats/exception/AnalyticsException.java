package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class AnalyticsException extends CustomException{
    public AnalyticsException(ErrorType errorType) {
        super(errorType);
    }
}

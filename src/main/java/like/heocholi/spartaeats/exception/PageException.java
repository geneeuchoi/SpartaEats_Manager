package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class PageException extends CustomException {
	public PageException(ErrorType errorType) {
		super(errorType);
	}
}

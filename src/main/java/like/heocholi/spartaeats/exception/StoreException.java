package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class StoreException extends CustomException {
	public StoreException(ErrorType errorType) {
		super(errorType);
	}
}

package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class OrderException extends CustomException {
	public OrderException(ErrorType errorType) {
		super(errorType);
	}
}

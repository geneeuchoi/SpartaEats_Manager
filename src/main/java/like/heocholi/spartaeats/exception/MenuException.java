package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class MenuException  extends CustomException{
	public MenuException(ErrorType message) {
		super(message);
	}
}

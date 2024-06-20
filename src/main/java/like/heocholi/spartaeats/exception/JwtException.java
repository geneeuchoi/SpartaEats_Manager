package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;
import lombok.Getter;


@Getter
public class JwtException extends CustomException{

    public JwtException(ErrorType errorType) {
        super(errorType);
    }
}

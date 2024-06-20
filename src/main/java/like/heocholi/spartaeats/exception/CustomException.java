package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public abstract class CustomException extends RuntimeException {
    private ErrorType errorType;
}

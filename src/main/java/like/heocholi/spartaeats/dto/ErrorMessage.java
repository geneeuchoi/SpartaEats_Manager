package like.heocholi.spartaeats.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage<T> {
	private Integer statusCode;
	private T message;
}

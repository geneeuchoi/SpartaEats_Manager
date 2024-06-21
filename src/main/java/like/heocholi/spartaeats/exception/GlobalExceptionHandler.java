package like.heocholi.spartaeats.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import like.heocholi.spartaeats.dto.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	//@Valid 어노테이션을 통해 검증에 실패하면 발생하는 예외를 처리하는 메소드
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		
		Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(
				error -> error.getField(),
				error -> error.getDefaultMessage(),
				(existingValue, newValue) -> existingValue
			));
		
		ErrorMessage errorMessage = ErrorMessage.builder()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.message(errors)
			.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorMessage<String>> handleCustomException(CustomException e) {
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(e.getErrorType().getHttpStatus().value())
				.message(e.getErrorType().getMessage())
				.build();

		return ResponseEntity.status(e.getErrorType().getHttpStatus()).body(errorMessage);
	}
	
	@ExceptionHandler({ContentNotFoundException.class, PageException.class})
	public ResponseEntity<ErrorMessage<String>> handleMenuException(MenuException e) {
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
}

package like.heocholi.spartaeats.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // user
    DUPLICATE_ACCOUNT_ID(HttpStatus.LOCKED, "이미 아이디가 존재합니다."),
    INVALID_ACCOUNT_ID(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    DEACTIVATE_USER(HttpStatus.FORBIDDEN, "이미 탈퇴한 회원입니다."),
    DUPLICATE_PASSWORD(HttpStatus.LOCKED, "중복된 비밀번호입니다."),
    NO_AUTHENTICATION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    REQUIRES_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),

    // JWT
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),
    EXPIRED_JWT(HttpStatus.FORBIDDEN, "만료된 JWT 입니다."),
    LOGGED_OUT_TOKEN(HttpStatus.FORBIDDEN, "이미 로그아웃된 토큰입니다."),
    
    // order
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다."),
    INVALID_ORDER_STORE(HttpStatus.BAD_REQUEST, "본인의 가게 주문만 조회할 수 있습니다."),
    
    // store
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."),
    
    // page
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "페이지 번호가 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

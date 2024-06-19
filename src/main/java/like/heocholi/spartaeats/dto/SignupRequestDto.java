package like.heocholi.spartaeats.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "사용자 아이디를 입력해 주세요.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "알파벳 소문자와 숫자로 구성된 4자 이상, 10자 이하여야 합니다.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
            message = "최소 8자 이상, 15자 이하의 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
    private String password;
}

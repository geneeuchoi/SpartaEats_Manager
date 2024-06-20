package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Manager;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String userId;

    public SignupResponseDto(Manager manager) {
        this.userId = manager.getUserId();
    }
}

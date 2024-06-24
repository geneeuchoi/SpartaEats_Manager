package like.heocholi.spartaeats.dto;

import jakarta.validation.constraints.NotNull;
import like.heocholi.spartaeats.constants.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreRequestDto {

    @NotNull(message = "이름은 필수 입력 값입니다.")
    private final String name;

    @NotNull(message = "주소는 필수 입력 값입니다.")
    private final String address;

    @NotNull
    private final RestaurantType type;

}

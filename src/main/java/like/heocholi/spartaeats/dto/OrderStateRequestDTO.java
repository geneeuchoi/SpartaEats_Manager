package like.heocholi.spartaeats.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderStateRequestDTO {
	@NotNull
	private int state;
}

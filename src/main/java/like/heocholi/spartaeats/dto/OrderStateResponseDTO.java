package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Order;
import lombok.Getter;

@Getter
public class OrderStateResponseDTO {
	private Long orderId;
	private String state;
	
	
	public OrderStateResponseDTO(Order order) {
		this.orderId = order.getId();
		this.state = order.getState().getStatus();
	}
}

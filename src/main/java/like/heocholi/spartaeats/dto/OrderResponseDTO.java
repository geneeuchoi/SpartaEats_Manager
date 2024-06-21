package like.heocholi.spartaeats.dto;

import java.util.List;

import like.heocholi.spartaeats.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDTO {
	private Long orderId;
	private String storeName;
	private String customerName;
	private String orderState;
	private List<String> menuNames;
	private int totalPrice;
	private String orderDate;
	
	public OrderResponseDTO(Order order) {
		this.orderId = order.getId();
		this.storeName = order.getStore().getName();
		this.customerName = order.getCustomer().getName();
		this.orderState = order.getState().getStatus();
		this.menuNames = order.getOrderMenuList().stream().map(orderMenu -> orderMenu.getMenu().getName()).toList();
		this.totalPrice = order.getTotalPrice();
		this.orderDate = order.getCreatedAt().toString();
	}
}

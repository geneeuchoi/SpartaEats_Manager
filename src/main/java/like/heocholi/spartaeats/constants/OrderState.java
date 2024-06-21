package like.heocholi.spartaeats.constants;

import lombok.Getter;

@Getter
public enum OrderState {
	NONE("비주문상태"),
	ORDER_CANCELLED("주문취소"),
	ORDER_WAITING("주문대기"),
	FOOD_READY("음식준비"),
	DELIVERING("배달중"),
	FINISH("배달완료");
	
	private final String status;
	
	OrderState(String status) {
		this.status = status;
	}
}

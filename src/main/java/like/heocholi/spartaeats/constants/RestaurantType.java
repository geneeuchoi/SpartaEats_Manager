package like.heocholi.spartaeats.constants;

import lombok.Getter;

@Getter
public enum RestaurantType {
	KOREAN("한식"),
	CHINESE("중식"),
	JAPANESE("일식"),
	WESTERN("양식"),
	CHICKEN("치킨"),
	PIZZA("피자"),
	BURGER("버거"),
	STREET_FOOD("분식");
	
	private final String type;
	
	RestaurantType(String type) {
		this.type = type;
	}
}

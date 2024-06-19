package like.heocholi.spartaeats.constants;

import lombok.Getter;

@Getter
public enum UserRole {
	ROLE_CUSTOMER("고객"),
	ROLE_MANAGER("점주"),
	ROLE_ADMIN("관리자");
	
	private final String status;
	
	UserRole(String status) {
		this.status = status;
	}
}

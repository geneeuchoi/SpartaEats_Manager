package like.heocholi.spartaeats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import like.heocholi.spartaeats.constants.UserRole;
import like.heocholi.spartaeats.constants.UserStatus;
import like.heocholi.spartaeats.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "managers")
@NoArgsConstructor
public class Manager extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userId;
	
	private String password;
	
	private String refreshToken;

	@Enumerated(value = EnumType.STRING)
	private UserStatus userStatus;

	@Enumerated(value = EnumType.STRING)
	private UserRole role;
	
	@OneToOne(mappedBy = "manager", fetch = FetchType.LAZY)
	private Store store;

	public Manager(SignupRequestDto requestDto, String encodedPassword) {
		this.userId = requestDto.getUserId();
		this.password = encodedPassword;
		this.userStatus = UserStatus.ACTIVE;
		this.role = UserRole.ROLE_CUSTOMER;
	}

	public void saveRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public boolean validateRefreshToken(String refreshToken){
		if(this.refreshToken != null && this.refreshToken.equals(refreshToken)){
			return true;
		}
		return false;
	}

	public void withdrawManager() {
		this.userStatus = UserStatus.DEACTIVATE;
	}

	public void removeRefreshToken() {
		this.refreshToken = "";
	}
}

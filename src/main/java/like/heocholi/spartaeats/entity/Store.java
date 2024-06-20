package like.heocholi.spartaeats.entity;

import java.util.List;

import jakarta.persistence.*;
import like.heocholi.spartaeats.constants.RestaurantType;
import like.heocholi.spartaeats.dto.StoreRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@JoinColumn(name = "manager_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Manager manager;

	private String address;

	@Enumerated(EnumType.STRING)
	private RestaurantType type;

	@OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST)
	List<Menu> menuList;

	@Builder
	public Store(StoreRequestDto requestDto, Manager manager) {
		this.name = requestDto.getName();
		this.manager = manager;
		this.address = requestDto.getAddress();
		this.type = requestDto.getType();
	}

	public void update(StoreRequestDto requestDto) {
		this.name = requestDto.getName();
		this.address = requestDto.getAddress();
		this.type = requestDto.getType();
	}
}
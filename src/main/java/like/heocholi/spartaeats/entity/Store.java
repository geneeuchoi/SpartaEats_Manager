package like.heocholi.spartaeats.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import like.heocholi.spartaeats.constants.RestaurantType;

@Entity
@Table(name = "stores")
public class Store extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Manager manager;
	
	private String address;
	
	@Enumerated(EnumType.STRING)
	private RestaurantType type;
	
	@OneToMany(mappedBy = "store", orphanRemoval = true)
	List<Menu> menuList;
}
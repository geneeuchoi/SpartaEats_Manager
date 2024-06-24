package like.heocholi.spartaeats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "menus")
@NoArgsConstructor
public class Menu extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private int price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;


	public Menu(String name, int price, Store store) {
		this.name = name;
		this.price = price;
		this.store = store;
	}

	public void update(String name, int price) {
		this.name = name!=null ? name : this.name;
		this.price = price!=0 ? price : this.price;
	}
}

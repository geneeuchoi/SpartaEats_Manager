package like.heocholi.spartaeats.dto.menu;

import like.heocholi.spartaeats.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private String storeName;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.storeName = menu.getStore().getName();
    }
}

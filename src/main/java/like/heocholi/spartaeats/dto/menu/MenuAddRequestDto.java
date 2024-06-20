package like.heocholi.spartaeats.dto.menu;

import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Store;
import lombok.Getter;

@Getter
public class MenuAddRequestDto {

    private String name;
    private int price;

    public MenuAddRequestDto(Menu menu) {
        this.name = menu.getName();
        this.price = menu.getPrice();
    }


}

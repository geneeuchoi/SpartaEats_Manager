package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Menu;
import lombok.Getter;

@Getter
public class BestMenusResponseDto {
    private Long menuId;
    private String menuName;
    private Long orderCount;

    public BestMenusResponseDto(Menu menu, Long orderCount) {
        this.menuId = menu.getId();
        this.menuName = menu.getName();
        this.orderCount = orderCount;
    }
}

package like.heocholi.spartaeats.dto.menu;

import like.heocholi.spartaeats.entity.Menu;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private String storeName;

    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.storeName = menu.getStore().getName();

        this.created_at = menu.getCreatedAt();
        this.modified_at = menu.getModifiedAt();
    }
}

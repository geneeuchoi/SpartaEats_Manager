package like.heocholi.spartaeats.dto.menu;


import lombok.Getter;

@Getter
public class MenuUpdateRequestDto {

    private String name;
    private int price;

    public MenuUpdateRequestDto(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

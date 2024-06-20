package like.heocholi.spartaeats.dto.menu;


import lombok.Getter;

@Getter
public class MenuAddRequestDto {

    private String name;
    private int price;

    public MenuAddRequestDto(String name,int price,Long storeId) {
        this.name = name;
        this.price = price;
    }
}

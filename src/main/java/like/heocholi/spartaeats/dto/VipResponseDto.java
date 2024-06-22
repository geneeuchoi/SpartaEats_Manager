package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Customer;
import lombok.Getter;

@Getter
public class VipResponseDto {
    private String vipId;
    private Long orderCount;

    public VipResponseDto(Customer customer, Long orderCount){
        this.vipId = customer.getUserId();
        this.orderCount = orderCount;
    }
}

package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Customer;
import lombok.Getter;

@Getter
public class CustomerResponseDTO {
    private String userId;
    private String name;

    public CustomerResponseDTO(Customer customer) {
        this.userId = customer.getUserId();
        this.name = customer.getName();
    }
}

package like.heocholi.spartaeats.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import like.heocholi.spartaeats.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListResponseDTO {
	private Integer currentPage;
	private Long totalElements;
	private Integer totalPages;
	private Integer size;
	private List<OrderResponseDTO> orderList;
	
	public OrderListResponseDTO(Integer currentPage, Page<Order> orderPage) {
		this.currentPage = currentPage;
		this.totalElements = orderPage.getTotalElements();
		this.totalPages = orderPage.getTotalPages();
		this.size = orderPage.getSize();
		this.orderList = orderPage.getContent().stream().map(OrderResponseDTO::new).toList();
	}
}

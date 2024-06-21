package like.heocholi.spartaeats.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import like.heocholi.spartaeats.dto.OrderListResponseDTO;
import like.heocholi.spartaeats.dto.OrderResponseDTO;
import like.heocholi.spartaeats.dto.OrderStateRequestDTO;
import like.heocholi.spartaeats.dto.OrderStateResponseDTO;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.ContentNotFoundException;
import like.heocholi.spartaeats.exception.PageException;
import like.heocholi.spartaeats.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final StoreService storeService;
	
	//전체 주문 내역 조회
	public OrderListResponseDTO getStoreOrders(Long storeId, Integer page, Manager manager) {
		Store store = storeService.findStore(storeId, manager);
		
		Pageable pageable = createPageable(page);
		Page<Order> orderPage = orderRepository.findAllByStore(store, pageable);
		
		checkValidatePage(page, orderPage);
		
		return new OrderListResponseDTO(page, orderPage);
	}
	
	//주문 상세 조회
	public OrderResponseDTO getStoreOrderDetails(Long storeId, Long orderId, Manager manager) {
		Store store = storeService.findStore(storeId, manager);
		Order order = getOrder(orderId);
		
		checkValidateStore(order, store);
		
		return new OrderResponseDTO(order);
	}
	
	//주문 상태 변경
	@Transactional
	public OrderStateResponseDTO changeOrderState(Long storeId, Long orderId, OrderStateRequestDTO requestDTO, Manager manager) {
		Store store = storeService.findStore(storeId, manager);
		Order order = getOrder(orderId);
		checkValidateStore(order, store);
		
		order.updateStatus(requestDTO.getState());
		
		return new OrderStateResponseDTO(order);
	}
	
	//페이지 생성
	private Pageable createPageable(Integer page) {
		return PageRequest.of(page-1, 5, Sort.by("createdAt").descending());
	}
	
	//페이지 유효성 검사
	private static void checkValidatePage(Integer page, Page<Order> orderPage) {
		if (orderPage.getTotalElements() == 0) {
			throw new ContentNotFoundException("주문 내역이 없습니다.");
		}
		
		if (page > orderPage.getTotalPages() || page < 1) {
			throw new PageException("페이지가 존재하지 않습니다.");
		}
	}
	
	//가게 유효성 검사
	private static void checkValidateStore(Order order, Store store) {
		if (!order.getStore().equals(store)) {
			throw new PageException("본인 가게의 주문 내역만 조회할 수 있습니다.");
		}
	}
	
	//주문 조회
	private Order getOrder(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문 내역이 존재하지 않습니다."));
	}
}

package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.exception.AnalyticsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.OrderException;
import like.heocholi.spartaeats.exception.PageException;
import like.heocholi.spartaeats.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
			throw new OrderException(ErrorType.NOT_FOUND_ORDER);
		}
		
		if (page > orderPage.getTotalPages() || page < 1) {
			throw new PageException(ErrorType.INVALID_PAGE);
		}
	}
	
	//가게 유효성 검사
	private static void checkValidateStore(Order order, Store store) {
		if (!order.getStore().equals(store)) {
			throw new OrderException(ErrorType.INVALID_ORDER_STORE);
		}
	}
	
	//주문 조회
	private Order getOrder(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderException(ErrorType.NOT_FOUND_ORDER));
	}

	// vip 고객 조회
	public List<VipResponseDto> getVipList(Store store) {
		return orderRepository.getVips(store).orElseThrow(
				() -> new AnalyticsException(ErrorType.NOT_FOUND_ORDER)
		);
	}

	// 날짜별 판매량 조회
	public List<DailySalesResponseDto> getDailySales(Store store){
		return orderRepository.getDailySales(store).orElseThrow(
				()-> new AnalyticsException(ErrorType.NOT_FOUND_ORDER)
		);
	}
}

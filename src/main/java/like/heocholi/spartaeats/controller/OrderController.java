package like.heocholi.spartaeats.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.OrderListResponseDTO;
import like.heocholi.spartaeats.dto.OrderResponseDTO;
import like.heocholi.spartaeats.dto.OrderStateRequestDTO;
import like.heocholi.spartaeats.dto.OrderStateResponseDTO;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/orders")
public class OrderController {
	private final OrderService orderService;
	
	// 가게 - 주문 목록 불러오기
	@GetMapping
	public ResponseEntity<ResponseMessage<OrderListResponseDTO>> getStoreOrder(
		@PathVariable Long storeId,
		@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		OrderListResponseDTO responseDTO = orderService.getStoreOrders(storeId, page, userDetails.getManager());
		
		ResponseMessage<OrderListResponseDTO> responseMessage = ResponseMessage.<OrderListResponseDTO>builder()
			.statusCode(HttpStatus.OK.value())
			.message("주문 목록을 불러왔습니다.")
			.data(responseDTO)
			.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
	}
	
	// 가게 - 주문 상세 정보 불러오기
	@GetMapping("/{orderId}")
	public ResponseEntity<ResponseMessage<OrderResponseDTO>> getStoreOrderDetail(@PathVariable Long storeId, @PathVariable Long orderId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		OrderResponseDTO responseDTO = orderService.getStoreOrderDetails(storeId, orderId, userDetails.getManager());
		
		ResponseMessage<OrderResponseDTO> responseMessage = ResponseMessage.<OrderResponseDTO>builder()
			.statusCode(HttpStatus.OK.value())
			.message("주문 상세 정보를 불러왔습니다.")
			.data(responseDTO)
			.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
	}
	
	// 가게 - 주문 상태 변경하기
	@PutMapping("{orderId}")
	public ResponseEntity<ResponseMessage<OrderStateResponseDTO>> changeOrderStatus(@PathVariable Long storeId, @PathVariable Long orderId,
		@Valid @RequestBody OrderStateRequestDTO requestDTO,@AuthenticationPrincipal UserDetailsImpl userDetails) {
		OrderStateResponseDTO responseDTO = orderService.changeOrderState(storeId, orderId, requestDTO, userDetails.getManager());
		
		ResponseMessage<OrderStateResponseDTO> responseMessage = ResponseMessage.<OrderStateResponseDTO>builder()
			.statusCode(HttpStatus.OK.value())
			.message("주문 상태가 변경되었습니다.")
			.data(responseDTO)
			.build();
	
		return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
	}
	
}

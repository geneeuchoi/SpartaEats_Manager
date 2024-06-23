package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.BestMenusResponseDto;
import like.heocholi.spartaeats.dto.DailyOrdersResponseDto;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.VipResponseDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;


    /**
     * VIP 고객 리스트 (주문 많이한 순 5명) 조회 API
     * @param storeId 가게Id
     * @param userDetails 로그인한 매니저 정보
     * @return VIP 고객 및 주문량 리스트
     */
    @GetMapping("/customers/vip")
    public ResponseEntity<ResponseMessage<List<VipResponseDto>>> getVipList(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Manager manager = userDetails.getManager();
        List<VipResponseDto> vipList = analyticsService.getVipList(storeId, manager);

        ResponseMessage<List<VipResponseDto>> responseMessage = ResponseMessage.<List<VipResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("VIP 조회 성공")
                .data(vipList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 인기 메뉴 조회 API
     * @param storeId 가게Id
     * @param userDetails 로그인한 매니저 정보
     * @return 인기 메뉴 및 주문량 리스트
     */
    @GetMapping("/orders/top-menus")
    public ResponseEntity<ResponseMessage<List<BestMenusResponseDto>>> getBestMenus(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Manager manager = userDetails.getManager();
        List<BestMenusResponseDto> bestMenus = analyticsService.getBestMenus(storeId, manager);

        ResponseMessage<List<BestMenusResponseDto>> responseMessage = ResponseMessage.<List<BestMenusResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("BestMenu 조회 성공")
                .data(bestMenus)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 날짜별 주문건수 조회 API
     * @param storeId 가게Id
     * @param userDetails 로그인한 매니저 정본
     * @return 날짜별 주문 건수 및 매출 리스트
     */
    @GetMapping("/orders/count-by-date")
    public ResponseEntity<ResponseMessage<List<DailyOrdersResponseDto>>> getOrdersByDate(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Manager manager = userDetails.getManager();
        List<DailyOrdersResponseDto> ordersByDate = analyticsService.getDailySales(storeId, manager);

        ResponseMessage<List<DailyOrdersResponseDto>> responseMessage = ResponseMessage.<List<DailyOrdersResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("날짜별 주문 건수 조회 성공")
                .data(ordersByDate)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}


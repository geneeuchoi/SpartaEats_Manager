package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.BestMenusResponseDto;
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
     * VIP 고객 리스트 (주문 많이한 순 5명)
     * @param storeId 가게Id
     * @param userDetails 로그인한 매니저 정보
     *
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

}


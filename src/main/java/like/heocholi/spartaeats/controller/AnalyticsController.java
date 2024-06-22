package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.CustomerResponseDTO;
import like.heocholi.spartaeats.dto.ResponseMessage;
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

    //가게별 찜하기 수 보기
    @GetMapping("/picks/count")
    public ResponseEntity<ResponseMessage<Long>> getPickCount(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long pickCount = analyticsService.getPickCount(storeId, userDetails.getManager());

        ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
                .statusCode(HttpStatus.OK.value())
                .message("찜하기 수 조회가 완료되었습니다.")
                .data(pickCount)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //찜하기 한 customer 리스트 보기
    @GetMapping("/picks/customers")
    public ResponseEntity<ResponseMessage<List<CustomerResponseDTO>>> getPickCustomers(@PathVariable Long storeId,
                                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<CustomerResponseDTO> responseDTO = analyticsService.getPickCustomers(storeId, userDetails.getManager());

        ResponseMessage<List<CustomerResponseDTO>> responseMessage = ResponseMessage.<List<CustomerResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("찜하기 한 customer 리스트 조회가 완료되었습니다.")
                .data(responseDTO)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

    }
}

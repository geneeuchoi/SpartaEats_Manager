package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.StoreRequestDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    // 음식점 등록
    @PostMapping
    public ResponseEntity<ResponseMessage<StoreResponseDto>> createStore(@Valid @RequestBody StoreRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StoreResponseDto responseDto = storeService.createStore(requestDto, userDetails);

        ResponseMessage<StoreResponseDto> responseMessage = ResponseMessage.<StoreResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("가게가 성공적으로 등록되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);

    }


    // 사용자의 모든 음식점 정보 보기
    @GetMapping
    public ResponseEntity<ResponseMessage<List<StoreResponseDto>>> readAllStore(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<StoreResponseDto> responseDtoList = storeService.readAllStore(userDetails);

        ResponseMessage<List<StoreResponseDto>> responseMessage = ResponseMessage.<List<StoreResponseDto>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("모든 가게 정보를 성공적으로 불러왔습니다.")
                .data(responseDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }


    // 사용자의 특정 음식점 정보 보기
    @GetMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<StoreResponseDto>> readStore(@AuthenticationPrincipal UserDetailsImpl userDetail, @PathVariable Long storeId) {
        StoreResponseDto responseDto = storeService.readStore(userDetail, storeId);

        ResponseMessage<StoreResponseDto> responseMessage = ResponseMessage.<StoreResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("가게 정보를 성공적으로 불러왔습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // 음식점 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<StoreResponseDto>> updateStore(@Valid @RequestBody StoreRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long storeId) {
        StoreResponseDto responseDto = storeService.updateStore(requestDto, userDetails, storeId);

        ResponseMessage<StoreResponseDto> responseMessage = ResponseMessage.<StoreResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("가게 정보를 성공적으로 수정했습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // 음식점 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<Long>> deleteStore(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long storeId) {
        Long id = storeService.deleteStore(userDetails, storeId);

        ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("가게를 성공적으로 삭제했습니다.")
                .data(id)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);

    }
}

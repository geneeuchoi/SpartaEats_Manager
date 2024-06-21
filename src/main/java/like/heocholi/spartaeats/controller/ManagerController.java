package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.SignupResponseDto;
import like.heocholi.spartaeats.dto.WithdrawRequestDto;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.service.ManagerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    /**
     * 회원가입 API
     * @param requestDto 회원가입 정보
     * @return 회원 정보, 응답 상태, 메시지
     */
    @PostMapping
    public ResponseEntity<ResponseMessage<SignupResponseDto>> signup(@RequestBody @Valid SignupRequestDto requestDto){
        SignupResponseDto responseDto = managerService.signup(requestDto);

        ResponseMessage<SignupResponseDto> responseMessage = ResponseMessage.<SignupResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("회원가입 성공")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    /**
     * 로그아웃 API
     * @param userDetails 회원 정보
     * @return 회원Id, 응답 상태, 메시지
     */
    @PutMapping("/logout")
    public ResponseEntity<ResponseMessage<String>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String userId = managerService.logout(userDetails.getUsername());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("로그아웃 성공")
                .data(userId)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 회원탈퇴 API
     * @param requestDto 회원탈퇴 정보(비밀번호)
     * @param userDetails 회원 정보
     * @return 회원Id, 응답 상태, 메시지
     */
    @PutMapping("/withdraw")
    public ResponseEntity<ResponseMessage<String>> withdrawManager(@RequestBody WithdrawRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getUsername();
        String withdrawnUserId = managerService.withdrawManager(requestDto, userId);

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원 탈퇴가 완료.")
                .data(withdrawnUserId)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}

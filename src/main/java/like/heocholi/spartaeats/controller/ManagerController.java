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

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto requestDto){
        SignupResponseDto responseDto = managerService.signup(requestDto);

        ResponseMessage<SignupResponseDto> responseMessage = ResponseMessage.<SignupResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원가입 성공")
                .data(responseDto)
                .build();

        return ResponseEntity.ok().body(responseMessage);
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String userId = managerService.logout(userDetails.getUsername());
        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("로그아웃 성공")
                .data(userId)
                .build();

        return ResponseEntity.ok().body(responseMessage);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> withdrawManager(@RequestBody WithdrawRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getUsername();
        String withdrawnUserId = managerService.withdrawManager(requestDto, userId);

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원 탈퇴가 완료.")
                .data(withdrawnUserId)
                .build();

        return ResponseEntity.ok().body(responseMessage);
    }
}

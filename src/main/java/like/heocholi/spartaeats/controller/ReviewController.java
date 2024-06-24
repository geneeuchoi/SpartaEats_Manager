package like.heocholi.spartaeats.controller;


import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.ReviewResponseDto;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 조회
    @GetMapping("/stores/{storeId}/reviews/date")
    public ResponseEntity<ResponseMessage<List<ReviewResponseDto>>> getReviewSortDate(@PathVariable Long storeId,
                                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<ReviewResponseDto> reviewList = reviewService.getReviewSortDate(storeId,userDetails.getManager());

        ResponseMessage<List<ReviewResponseDto>> responseMessage = ResponseMessage.<List<ReviewResponseDto>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("모든 리뷰가 조회되었습니다.(날짜순)")
                .data(reviewList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //리뷰 조회
    @GetMapping("/stores/{storeId}/reviews/like")
    public ResponseEntity<ResponseMessage<List<ReviewResponseDto>>> getReviewSortLike(@PathVariable Long storeId) {

        List<ReviewResponseDto> reviewList = reviewService.getReviewSortLike(storeId);

        ResponseMessage<List<ReviewResponseDto>> responseMessage = ResponseMessage.<List<ReviewResponseDto>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("모든 리뷰가 조회되었습니다.(좋아요순)")
                .data(reviewList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}

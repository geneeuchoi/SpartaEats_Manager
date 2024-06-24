package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.ReviewResponseDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Review;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.ReviewException;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final StoreRepository storeRepository;

    public List<ReviewResponseDto> getReviewSortDate(Long storeId, Manager manager) {

        List<ReviewResponseDto> reviewList = getReviewList(storeId,manager);

        return reviewList.stream()
                .sorted((r2, r1) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getReviewSortLike(Long storeId,Manager manager) {

        List<ReviewResponseDto> reviewList = getReviewList(storeId,manager);

        return reviewList.stream()
                .sorted(Comparator.comparing(ReviewResponseDto::getLikeCount).reversed()
                        .thenComparing(Comparator.comparing(ReviewResponseDto::getCreatedAt).reversed()))
                .collect(Collectors.toList());
    }



    /* Util */

    public List<ReviewResponseDto> getReviewList(Long storeId, Manager manager){
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new ReviewException(ErrorType.NOT_FOUND_STORES)
        );

        if(store.getManager().equals(manager)){
            throw new ReviewException(ErrorType.NOT_EQUAL_MANAGER);
        }

        List<ReviewResponseDto> reviewList = store.getReviews().stream().map(ReviewResponseDto::new).toList();
        return reviewList;
    }

}

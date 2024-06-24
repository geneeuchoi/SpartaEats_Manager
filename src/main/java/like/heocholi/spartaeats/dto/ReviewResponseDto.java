package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {

    private Long id;

    private String storeName;
    private String customerName;
    private String contents;

    private int likeCount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.storeName = review.getStore().getName();
        this.customerName = review.getCustomer().getName();
        this.contents = review.getContents();

        this.likeCount = review.getLikeCount();
        this.createdAt = review.getCreatedAt();
        this.modifiedAt = review.getModifiedAt();
    }

}

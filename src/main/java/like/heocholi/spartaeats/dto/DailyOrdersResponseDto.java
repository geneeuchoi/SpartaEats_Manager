package like.heocholi.spartaeats.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class DailyOrdersResponseDto {
    private Date date;
    private Long orderCount;
    private Long sales;

    public DailyOrdersResponseDto(Date date, Long orderCount, Long sales) {
        this.date = date;
        this.orderCount = orderCount;
        this.sales = sales;
    }
}

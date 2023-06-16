package com.purgenta.gameshop.requests.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderReviewRequest {
    @Size(min = 15, max = 500)
    String review;
    @Min(1)
    @Max(5)
    int deliveryRating;
    @Min(1)
    @Max(5)
    int serviceRating;
}

package com.purgenta.gameshop.response.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEligibility {
    private boolean canReview;
}

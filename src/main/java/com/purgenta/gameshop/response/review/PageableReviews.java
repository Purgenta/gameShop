package com.purgenta.gameshop.response.review;

import com.purgenta.gameshop.models.review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageableReviews {
    private int pageCount;
    private List<Review> reviews;
}

package cz.cvut.fel.service;

import cz.cvut.fel.model.Rating;
import cz.cvut.fel.web.client.dto.ReviewDto;

public interface RatingService {
    
    ReviewDto convertToReviewDto(Rating rating);
    
    Rating convertToModel(Rating rating, ReviewDto reviewDto);
    
    Double getProductRating(Long productId);
    
    ReviewDto getReview(Long productId);
    
    void addReview(Long productId, ReviewDto reviewDto);
}

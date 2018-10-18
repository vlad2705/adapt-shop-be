package cz.cvut.fel.web.client;

import cz.cvut.fel.service.RatingService;
import cz.cvut.fel.web.client.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {
    
    private final RatingService ratingService;
    
    @Autowired
    public ReviewController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    
    @GetMapping("/product/{productId}/review")
    public ReviewDto getReview(@PathVariable Long productId) {
        return ratingService.getReview(productId);
    }
    
    @PutMapping("/product/{productId}/review")
    public void addReview(@PathVariable Long productId, @RequestBody ReviewDto reviewDto) {
        ratingService.addReview(productId, reviewDto);
    }
}

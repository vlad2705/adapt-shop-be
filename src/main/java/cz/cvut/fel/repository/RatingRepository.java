package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Rating;

public interface RatingRepository extends IDAO<Rating, Long> {
    
    Double findProductRating(long productId);
    
    Rating findByPersonAndProduct(long personId, long productId);
    
    void save(Rating rating);
}

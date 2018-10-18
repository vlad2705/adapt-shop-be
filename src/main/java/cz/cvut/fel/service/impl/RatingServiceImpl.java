package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Person;
import cz.cvut.fel.model.Rating;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.repository.RatingRepository;
import cz.cvut.fel.service.PersonService;
import cz.cvut.fel.service.RatingService;
import cz.cvut.fel.web.client.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final PersonService personService;
    
    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, ProductRepository productRepository, PersonService personService) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.personService = personService;
    }
    
    @Override
    public ReviewDto convertToReviewDto(Rating rating) {
        if (rating != null) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setRating(rating.getRating());
            reviewDto.setDescription(rating.getDescription());
            return reviewDto;
        }
        return null;
    }
    
    @Override
    public Rating convertToModel(Rating rating, ReviewDto reviewDto) {
        if (rating != null && reviewDto != null) {
            rating.setRating(reviewDto.getRating());
            rating.setDescription(reviewDto.getDescription());
            rating.setDate(LocalDateTime.now());
            return rating;
        }
        return null;
    }
    
    @Override
    public Double getProductRating(Long productId) {
        if (productId != null) {
            return ratingRepository.findProductRating(productId);
        }
        return null;
    }
    
    @Override
    public ReviewDto getReview(Long productId) {
        if (productId != null) {
            Person person = personService.getAuthorizationPerson();
            if (person == null) {
                throw new RuntimeException("Bad request");
            }
            Rating rating = ratingRepository.findByPersonAndProduct(person.getId(), productId);
            return convertToReviewDto(rating);
        }
        return null;
    }
    
    @Override
    public void addReview(Long productId, ReviewDto reviewDto) {
        if (productId != null && reviewDto != null) {
            Person person = personService.getAuthorizationPerson();
            if (person == null) {
                throw new RuntimeException("Bad request");
            }
            Rating rating = ratingRepository.findByPersonAndProduct(person.getId(), productId);
            if (rating == null) {
                rating = new Rating();
                rating.setPerson(person);
                rating.setProduct(productRepository.findById(productId));
            }
            convertToModel(rating, reviewDto);
            ratingRepository.save(rating);
        }
    }
}

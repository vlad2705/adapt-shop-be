package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Rating;
import cz.cvut.fel.repository.RatingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RatingRepositoryImpl extends AbstractDAO<Rating, Long> implements RatingRepository, ICanDelete<Rating> {
    
    @Override
    public Double findProductRating(long productId) {
        Query query = getEntityManager().createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.product.id = :productId");
        query.setParameter("productId", productId);
        return (Double) query.getSingleResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Rating findByPersonAndProduct(long personId, long productId) {
        Query query = getEntityManager().createQuery("SELECT r FROM Rating r WHERE r.person.id = :personId AND r.product.id = :productId");
        query.setParameter("personId", personId);
        query.setParameter("productId", productId);
        List<Rating> ratings = query.getResultList();
        return ratings != null && !ratings.isEmpty() ? ratings.get(0) : null;
    }
    
    @Override
    public BusinessCheckResult checkCanDelete(Rating rating) {
        return new BusinessCheckSimpleResult(rating != null, "This rating does not exist!");
    }
}

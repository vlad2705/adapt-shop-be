package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Picture;
import cz.cvut.fel.repository.PictureRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PictureRepositoryImpl extends AbstractDAO<Picture, Long> implements PictureRepository, ICanDelete<Picture> {

    @Override
    @SuppressWarnings("unchecked")
    public List<Picture> findByProduct(long productId) {
        Query query = getEntityManager().createQuery("SELECT p FROM Picture p WHERE p.product.id = :productId");
        query.setParameter("productId", productId);

        return query.getResultList();
    }

    public void save(Picture picture) {
            this.getEntityManager().persist(picture);
    }

    @Override
    public BusinessCheckResult checkCanDelete(Picture picture) {
        return new BusinessCheckSimpleResult(picture != null, "This picture does not exist!");
    }
}

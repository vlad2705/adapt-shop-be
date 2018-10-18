package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Picture;

import java.util.List;

public interface PictureRepository extends IDAO<Picture, Long> {

    List<Picture> findByProduct(long productId);

    void save(Picture picture);
}

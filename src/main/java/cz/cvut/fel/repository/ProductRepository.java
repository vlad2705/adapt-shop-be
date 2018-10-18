package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Picture;
import cz.cvut.fel.model.Product;
import cz.cvut.fel.web.client.dto.ClientProductDto;
import cz.cvut.fel.web.client.filter.ClientProductFilter;
import cz.cvut.fel.web.data.ProductData;
import cz.cvut.fel.web.filter.ProductFilter;

import java.util.List;

public interface ProductRepository extends IDAO<Product, Long> {

    long findRowCount(ProductFilter filter);

    List<ProductData> findByFilter(ProductFilter filter);
    
    List<ClientProductDto> findByClientFilter(ClientProductFilter filter);
    
    long findRowCountByClientFilter(ClientProductFilter filter);

    List<Product> findByCategory(long categoryId);
    
    List<Product> findByName(String name);
    
    Long findPictureIdByProduct(long productId);

    void save(Product product);
}

package cz.cvut.fel.web.client.dto;

import java.util.List;

public class ClientProductFilterDto {
    List<ClientProductDto> products;
    long totalProducts;
    
    public List<ClientProductDto> getProducts() {
        return products;
    }
    
    public void setProducts(List<ClientProductDto> products) {
        this.products = products;
    }
    
    public long getTotalProducts() {
        return totalProducts;
    }
    
    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }
}

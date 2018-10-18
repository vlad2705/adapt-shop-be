/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.model;

import cz.cvut.fel.model.builder.TestableProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author balikm1
 */
public class ProductTest {
    
    public static Product defaultProduct()
    {
        return create(1L, "Soccer ball", new BigDecimal(300), CategoryTest.defaultCategory(), "Description");
    }
    
    public static List<Product> defaultProductList() {
        return Arrays.asList(
                ProductTest.create(1L, "Product 1", BigDecimal.valueOf(1000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(2L, "Product 2", BigDecimal.valueOf(2000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(3L, "Product 3", BigDecimal.valueOf(3000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(4L, "Product 4", BigDecimal.valueOf(4000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(5L, "Product 5", BigDecimal.valueOf(5000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(6L, "Product 6", BigDecimal.valueOf(6000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(7L, "Product 7", BigDecimal.valueOf(7000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(8L, "Product 8", BigDecimal.valueOf(8000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(9L, "Product 9", BigDecimal.valueOf(9000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(10L, "Product 10", BigDecimal.valueOf(10000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(11L, "Product 11", BigDecimal.valueOf(11000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(12L, "Product 12", BigDecimal.valueOf(12000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(13L, "Product 13", BigDecimal.valueOf(13000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(14L, "Product 14", BigDecimal.valueOf(14000), CategoryTest.defaultCategory(), "Description"),
                ProductTest.create(15L, "Product 15", BigDecimal.valueOf(15000), CategoryTest.defaultCategory(), "Description")
        );
    }
    
    public static Product create(Long id, String name, BigDecimal price, Category category, String description)
    {
        return new TestableProduct.Builder()
           .setId(id)
           .setName(name)
           .setPrice(price)
           .setCategory(category)
           .setDescription(description)
           .build();
    }
}

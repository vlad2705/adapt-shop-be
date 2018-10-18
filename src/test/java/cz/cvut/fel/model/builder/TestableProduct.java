/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.model.builder;

import cz.cvut.fel.model.Category;
import cz.cvut.fel.model.Product;

import java.math.BigDecimal;

/**
 *
 * @author balikm1
 */
public class TestableProduct extends Product {
           
    public static class Builder {
        private TestableProduct productToBuild;

        public Builder() {
            productToBuild = new TestableProduct();
        }

        public TestableProduct build() {
            TestableProduct builtPerson = productToBuild;
            productToBuild = new TestableProduct();

            return builtPerson;
        }

        public Builder setId(Long id) {
            this.productToBuild.setId(id);
            return this;
        }

        public Builder setName(String name) {
            this.productToBuild.setName(name);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.productToBuild.setPrice(price);
            return this;
        }

        public Builder setCategory(Category category) {
            this.productToBuild.setCategory(category);
            return this;
        }
        
        public Builder setDescription(String description) {
            this.productToBuild.setDescription(description);
            return this;
        }
    }
}

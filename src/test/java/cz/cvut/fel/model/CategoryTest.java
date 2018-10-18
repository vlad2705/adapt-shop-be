/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.model;

import cz.cvut.fel.model.builder.TestableCategory;

/**
 *
 * @author balikm1
 */
public class CategoryTest {
    
    public static Category defaultCategory()
    {
        return create(16L, "Sport", null);
    }

    public static Category create(Long id, String name, Category parent)
    {
        return new TestableCategory.Builder()
                .setId(id)
                .setName(name)
                .setParent(parent)
                .build();
    }
}

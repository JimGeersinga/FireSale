package com.FireSale.api.mockdata;

import com.FireSale.api.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryData {
    public static List<Category> getCategories()
    {
        List<Category> categories = new ArrayList<>();
        for(int i = 1; i <= 10; i++)
        {
            Category category = new Category();
            category.setArchived(false);
            category.setName("Test categorie " + i);
            category.setId((long) i);
            categories.add(category);
        }
        return  categories;
    }

}

package com.example.shopapp.Services;

import com.example.shopapp.DTOS.CategoryDTO;
import com.example.shopapp.Models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(long id);
}

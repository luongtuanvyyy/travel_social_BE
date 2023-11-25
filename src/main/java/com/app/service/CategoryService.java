package com.app.service;

import com.app.entity.Category;
import com.app.payload.request.CategoryQueryParam;
import com.app.payload.response.APIResponse;

public interface CategoryService {
    APIResponse filterCategory (CategoryQueryParam categoryQueryParam);
    APIResponse getAll();

    APIResponse getCategoryProductCount();
    APIResponse create(Category category);
    APIResponse update(Category category);
    APIResponse delete(Integer id);

    APIResponse getExistCategory();
}

package com.app.service.serviceImpl;

import com.app.entity.Category;
import com.app.payload.request.CategoryQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.repository.CategoryRepository;
import com.app.service.CategoryService;
import com.app.speficication.CategorySpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategorySpecification categorySpecification;

    @Override
    public APIResponse filterCategory(CategoryQueryParam categoryQueryParam) {
        Specification<Category> spec = categorySpecification.getCategorySpecification(categoryQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(categoryQueryParam);
        Page<Category> response = categoryRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse getAll() {
        return new APIResponse(categoryRepository.findAll());
    }

    @Override
    public APIResponse getCategoryProductCount() {
        return new APIResponse(categoryRepository.getCategoryProductCount());
    }

    @Override
    public APIResponse create(Category category) {
        categoryRepository.save(category);
        return new APIResponse("Create Category Successful");
    }

    @Override
    public APIResponse update(Category category) {
        categoryRepository.save(category);
        return new APIResponse("Update Category Successful");
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
        return new APIResponse("Delete Category Successful");
    }

    @Override
    public APIResponse getExistCategory() {
        return new APIResponse(categoryRepository.getExistCategory());
    }
}

package com.app.api;

import com.app.entity.Category;
import com.app.payload.request.CategoryQueryParam;
import com.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryApi {
    @Autowired
    CategoryService categoryService;

    @GetMapping("public/categories/filter")
    public ResponseEntity<?> filterCategory(CategoryQueryParam categoryQueryParam) {
        return ResponseEntity.ok(categoryService.filterCategory(categoryQueryParam));
    }

    @GetMapping("/public/categories")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }


    @GetMapping("/public/categories-exist")
    public ResponseEntity<?> getExistCategory() {
        return ResponseEntity.ok(categoryService.getExistCategory());
    }

    @GetMapping("/public/categories/product-count")
    public ResponseEntity<?> getCategoryProductCount() {
        return ResponseEntity.ok(categoryService.getCategoryProductCount());
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @PutMapping("/admin/categories")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(category));
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }
}



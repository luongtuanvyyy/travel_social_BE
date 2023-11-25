package com.app.repository;

import com.app.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findAll(Specification<Category> spec, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from categories where id in (select c.id from categories c join products p on c.id = p.category_id group by c.id)")
    List<Category> getExistCategory();

    @Query("SELECT c.name, p.category.id, COUNT(p) AS product_count " +
            "FROM Product p " +
            "JOIN p.category c " +
            "GROUP BY p.category.id " +
            "ORDER BY COUNT(p) DESC")
    List<Object[]> getCategoryProductCount();
}

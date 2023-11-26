package com.app.repository;
import com.app.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Integer countByAccountId(Integer accountId);
    Page<Like> findAll(Specification<Like> spec, Pageable pageable);
}

package com.app.speficication;

import com.app.entity.Blog;
import com.app.entity.BlogReaction;
import com.app.entity.Tour;
import com.app.payload.request.BlogQueryParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.app.payload.request.BlogReactionQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class BlogSpecification {

    public Specification<Blog> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<Blog> getBlogSpecification(BlogQueryParam blogQueryParam) {
        Specification<Blog> spec = Specification.where(null);
        if (blogQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(blogQueryParam.getId()));
        }
        return spec;
    }

}

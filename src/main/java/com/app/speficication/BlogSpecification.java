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
    public Specification<Blog> hasNameLike(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
    }
    public Specification<Blog> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<BlogReaction> hasNameLikes(Integer keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), "%" + keyword + "%");
    }

    public Specification<Blog> getBlogSpecification(BlogQueryParam blogQueryParam) {
        Specification<Blog> spec = Specification.where(null);
        if (blogQueryParam.getTitle() != null) {
            spec = spec.and(hasNameLike(blogQueryParam.getTitle()));
        }
        if (blogQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(blogQueryParam.getId()));
        }
        return spec;
    }

    public Specification<BlogReaction> getBlogInteractionSpecification(
            BlogReactionQueryParam blogReactionQueryParam) {
        Specification<BlogReaction> spec = Specification.where(null);
        if (blogReactionQueryParam.getId() != null) {
            spec = spec.and(hasNameLikes(blogReactionQueryParam.getId()));
        }
        return spec;
    }

    public Specification<Blog> hasNameLikeBlog(String name) {
        return (Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(name.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("title")),
                    "%" + nameUpperCase + "%");
            return likePredicate;
        };
    }

    private String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}

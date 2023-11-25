package com.app.speficication;

import com.app.entity.BlogReaction;
import com.app.payload.request.BlogReactionQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BlogReactionSpecification {
    public Specification<BlogReaction>hasIdEqual(Integer id){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),id) );
    }
    public Specification<BlogReaction> getBlogReactionSpecitification(BlogReactionQueryParam blogReactionQueryParam) {
        Specification<BlogReaction> spec = Specification.where(null);
        if (blogReactionQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(blogReactionQueryParam.getId()));
        }
        return spec;
    }
}

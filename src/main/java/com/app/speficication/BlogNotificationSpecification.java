package com.app.speficication;

import com.app.entity.BlogNotification;
import com.app.payload.request.BlogNotificationQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.security.Timestamp;

@Component
public class BlogNotificationSpecification {

    public Specification<BlogNotification> hasidequal(Integer keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),keyword);
    }
    public Specification<BlogNotification>hasCreateTimeLike(Timestamp create_time){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("create_time"),"%"+create_time+"%") );
    }


    public Specification<BlogNotification> getAccountSpecification(BlogNotificationQueryParam blogNotificationQueryParam) {
        Specification<BlogNotification> spec = Specification.where(null);
        if (blogNotificationQueryParam.getId() != null) {
            spec = spec.and(hasidequal(blogNotificationQueryParam.getId()));
        }
        if (blogNotificationQueryParam.getCreate_time() != null) {
            spec = spec.and(hasCreateTimeLike(blogNotificationQueryParam.getCreate_time()));
        }
        return spec;
    }
}

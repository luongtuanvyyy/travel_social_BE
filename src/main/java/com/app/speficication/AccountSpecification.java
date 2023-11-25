package com.app.speficication;


import com.app.entity.Account;
import com.app.payload.request.AccountQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import javax.persistence.criteria.*;

@Component
public class AccountSpecification {
    public Specification<Account> hasNameLike(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
    }
    public Specification<Account> hasNamesLike(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }


    public Specification<Account> getAccountSpecification(AccountQueryParam accountQueryParam) {
        Specification<Account> spec = Specification.where(null);
        if (accountQueryParam.getEmail() != null) {
            spec = spec.and(hasNameLike(accountQueryParam.getEmail()));
        }
//        if (accountQueryParam.getFisrtName() != null) {
//            spec = spec.and(hasNamesLike(accountQueryParam.getFisrtName()));
//        }
        return spec;
    }
}

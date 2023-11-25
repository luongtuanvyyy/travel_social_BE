package com.app.speficication;

import com.app.entity.Payment;
import com.app.payload.request.PaymentQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PaymentSpecification {
    public Specification<Payment> hasNameLike(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),"%"+name+"%") );
    }
    public Specification<Payment> getPaymentSpecitification(PaymentQueryParam paymentQueryParam) {
        Specification<Payment> spec = Specification.where(null);
        if (paymentQueryParam.getName() != null) {
            spec = spec.and(hasNameLike(paymentQueryParam.getName()));
        }
        return spec;
    }
}

package com.app.speficication;


import com.app.entity.Voucher;
import com.app.payload.request.VoucherQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VoucherSpecification {

    public Specification<Voucher> hasPercentEqual(Integer percent) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("percent"), percent);
    }

    public Specification<Voucher> hasSizeEqual(Integer size) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("size"), size);
    }

    public Specification<Voucher> getVoucherSpecification(VoucherQueryParam voucherQueryParam) {
        Specification<Voucher> spec = Specification.where(null);
        if (voucherQueryParam.getId() != null) {
            spec = spec.and(hasPercentEqual(voucherQueryParam.getPercent()));
        }
        if (voucherQueryParam.getId() != null) {
            spec = spec.and(hasSizeEqual(voucherQueryParam.getSize()));
        }

        return spec;
    }
}

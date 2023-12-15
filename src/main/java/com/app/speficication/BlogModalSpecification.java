package com.app.speficication;

import java.text.Normalizer;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.app.modal.BlogModal;
import com.app.payload.request.BlogModalQueryParam;

@Component
public class BlogModalSpecification {
    public Specification<BlogModal> getBlogModalSpecification(BlogModalQueryParam blogModalQueryParam) {
        Specification<BlogModal> spec = Specification.where(null);
        return spec;
    }


    private String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}

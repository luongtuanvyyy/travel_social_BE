package com.app.utils;

import com.app.payload.request.BaseQueryRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class RequestParamsUtils {
    public Sort getSort(String sortField, String orderBy){
        return Sort.by(orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField );
    }
    public Pageable getPageable(BaseQueryRequest baseQueryRequest){
        Sort sort = getSort(baseQueryRequest.getSortField(), baseQueryRequest.getOrderBy());
        return PageRequest.of(baseQueryRequest.getPage(), baseQueryRequest.getPageSize(), sort);
    }
    public Pageable getPageableNoSort(BaseQueryRequest baseQueryRequest){
        return PageRequest.of(baseQueryRequest.getPage(), baseQueryRequest.getPageSize());
    }

}

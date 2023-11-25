package com.app.service;

import com.app.dto.OrderDto;
import com.app.payload.response.APIResponse;
import com.app.security.UserPrincipal;

public interface OrderService {
    APIResponse checkout(OrderDto dto, UserPrincipal userPrincipal);
    APIResponse getOrder(UserPrincipal userPrincipal);
}

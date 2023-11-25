package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountQueryParam extends BaseQueryRequest{
    String email;
}

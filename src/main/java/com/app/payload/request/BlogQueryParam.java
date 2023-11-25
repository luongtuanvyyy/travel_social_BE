package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogQueryParam extends BaseQueryRequest {
    String title;
    Integer id;
}

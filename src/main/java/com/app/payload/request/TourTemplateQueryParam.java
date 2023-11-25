package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourTemplateQueryParam extends BaseQueryRequest{
    String name;
    Integer id;
}

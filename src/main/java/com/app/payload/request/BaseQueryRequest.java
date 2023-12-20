package com.app.payload.request;

import lombok.Data;

@Data
public class BaseQueryRequest {
    int page = 0;
    int pageSize = 16;
    String orderBy = "desc";
    String sortField = "id";
    Boolean isActivated = Boolean.TRUE;
}

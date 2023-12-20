package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDataQueryParam extends BaseQueryRequest{
    public AccountDataQueryParam() {
        this.setPage(0);
        this.setPageSize(16);
        this.setOrderBy("desc");
        this.setSortField("followerCount");
    }
}

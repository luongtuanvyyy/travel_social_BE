package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogReactionQueryParam extends BaseQueryRequest {
    Integer id;
    LocalDateTime createTime;
    Integer blogId;
    Integer accountId;
}

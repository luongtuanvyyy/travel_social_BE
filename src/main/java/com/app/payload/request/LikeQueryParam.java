package com.app.payload.request;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LikeQueryParam extends BaseQueryRequest{
    Integer id;

    LocalDateTime like_time;
}

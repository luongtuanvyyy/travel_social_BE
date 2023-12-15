package com.app.payload.request;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FollowQueryParam extends BaseQueryRequest{

    Integer followerId;
}

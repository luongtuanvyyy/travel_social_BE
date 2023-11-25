package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ViewQueryParam extends BaseQueryRequest{
    Integer id;

    String user_id;

    String blog_id;

    LocalDateTime view_time;
}

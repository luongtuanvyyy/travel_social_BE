package com.app.payload.request;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class FavoriteQueryParam extends BaseQueryRequest{
    Integer id;

    Integer tourId;
}

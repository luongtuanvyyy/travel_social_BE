package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingQueryParam extends BaseQueryRequest{
        String status;
}

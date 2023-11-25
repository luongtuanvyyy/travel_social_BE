package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class VoucherQueryParam extends BaseQueryRequest{
    Integer id;

    Integer size;

    Integer percent;

    Timestamp time_start;

    Timestamp time_end;
}

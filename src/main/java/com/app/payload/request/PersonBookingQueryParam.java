package com.app.payload.request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonBookingQueryParam extends BaseQueryRequest{
    Integer id;

    String fullname;
}

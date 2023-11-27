package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleQueryParam extends BaseQueryRequest{
    Integer id;

    String name;

    String address;
    String type;
    String email;
    String website;
    String hotline;
}

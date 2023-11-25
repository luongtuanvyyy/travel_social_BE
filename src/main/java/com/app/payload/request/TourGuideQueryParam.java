package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourGuideQueryParam extends BaseQueryRequest{

    Integer id;

    String fullName;


}

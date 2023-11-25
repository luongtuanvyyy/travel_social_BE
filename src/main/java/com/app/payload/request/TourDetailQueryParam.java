package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TourDetailQueryParam extends BaseQueryRequest{
    Integer id;

    Date dates;

    String restaurant;

    String place;

    String vehicle;

    String hotel ;

    String tour;
}

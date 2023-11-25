package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourCancelQueryParam extends BaseQueryRequest{
         Integer id;
         Integer percent;
         Integer date;
         Integer TOUR_ID;
}

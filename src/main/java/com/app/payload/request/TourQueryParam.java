package com.app.payload.request;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.formula.functions.Today;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class TourQueryParam extends BaseQueryRequest {
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Integer id;
    Integer size;
    String name;
    String address;
    String vehicle;
    String departure;
    Boolean sortRegistered;
    @Temporal(TemporalType.TIMESTAMP)
    Date start_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date end_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    Date startDateBooking;
    @Temporal(TemporalType.TIMESTAMP)
    Date endDateBooking;
    Integer discount;
    @Temporal(TemporalType.TIMESTAMP)
    Date today;
}

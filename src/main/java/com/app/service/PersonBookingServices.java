package com.app.service;

import com.app.entity.Follow;
import com.app.entity.PersonBooking;
import com.app.payload.request.PersonBookingQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonBookingServices {
    APIResponse filterPersonBooking(PersonBookingQueryParam personBookingQueryParam);


    APIResponse create(PersonBooking personBooking);

    APIResponse update(PersonBooking personBooking);

    APIResponse delete(Integer id);

    APIResponse uploadExcel(MultipartFile excel);

    APIResponse createBatch(List<PersonBooking> personBookings);
}

package com.app.service.serviceImpl;


import com.app.entity.Blog;
import com.app.entity.BookingCancel;
import com.app.entity.BookingNotification;
import com.app.payload.request.BookingNotificationQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.BookingNotificationRepository;
import com.app.service.BookingNotificationServices;
import com.app.speficication.BookingNoficationSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingNotificationServicesImpl implements BookingNotificationServices {
    @Autowired
    BookingNotificationRepository bookingNotificationRepository;
    @Autowired
    BookingNoficationSpecification bookingNoficationSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterBookingNotification(BookingNotificationQueryParam bookingNotificationQueryParam) {
        try {
        Specification<BookingNotification> spec = bookingNoficationSpecification.getBookingNotificationSpectication(bookingNotificationQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(bookingNotificationQueryParam);
        Page<BookingNotification> response = bookingNotificationRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse create(BookingNotification bookingNotification) {
        try {
        bookingNotification = bookingNotificationRepository.save(bookingNotification);
        return new SuccessAPIResponse(bookingNotification);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(BookingNotification bookingNotification) {
        try {
        if(bookingNotification == null){
            return  new FailureAPIResponse("Booking notification id is required!");
        }
        BookingNotification exists = bookingNotificationRepository.findById(bookingNotification.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find booking notification with id: "+bookingNotification.getId());
        }
        bookingNotification = bookingNotificationRepository.save(bookingNotification);
        return new SuccessAPIResponse(bookingNotification);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }


    @Override
    public APIResponse delete(Integer id) {
        try {
            bookingNotificationRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, BookingNotification.class, bookingNotificationRepository);
    }

    @Override
    public APIResponse createBatch(List<BookingNotification> bookingNotifications) {
        List<BookingNotification> createdBookingNotifications = new ArrayList<>();
        for (BookingNotification bookingNotification : bookingNotifications) {
            BookingNotification createdBookingNotification = bookingNotificationRepository.save(bookingNotification);
            createdBookingNotifications.add(createdBookingNotification);
        }
        return new SuccessAPIResponse(createdBookingNotifications);
    }
}

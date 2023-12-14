package com.app.service.serviceImpl;

import com.app.entity.Blog;
import com.app.entity.Booking;
import com.app.entity.BookingCancel;
import com.app.payload.request.BookingCancelQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.BookingCancelRepository;
import com.app.service.BookingCancelServices;
import com.app.speficication.BookingCancelSpecification;
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
public class BookingCancelServiceimpl implements BookingCancelServices {
    @Autowired
    BookingCancelSpecification bookingCancelSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    BookingCancelRepository bookingCancelRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterBookingCancel(BookingCancelQueryParam bookingCancelQueryParam) {
        try {
        Specification<BookingCancel> spec = bookingCancelSpecification.getBookingCanceSpecitification(bookingCancelQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(bookingCancelQueryParam);
        Page<BookingCancel> response = bookingCancelRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }
    @Override
    public APIResponse create(BookingCancel bookingCancel) {
        try {
        bookingCancel = bookingCancelRepository.save(bookingCancel);
        return new SuccessAPIResponse(bookingCancel);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(BookingCancel bookingCancel) {
        try {

        if(bookingCancel == null){
            return  new FailureAPIResponse("Booking cancel id is required!");
        }
        BookingCancel exists = bookingCancelRepository.findById(bookingCancel.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find booking cancel with id: "+bookingCancel.getId());
        }
        bookingCancel = bookingCancelRepository.save(bookingCancel);
        return new SuccessAPIResponse(bookingCancel);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }



    @Override
    public APIResponse delete(Integer id) {
        try {
            bookingCancelRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, BookingCancel.class, bookingCancelRepository);
    }

    @Override
    public APIResponse createBatch(List<BookingCancel> bookingCancels) {
        List<BookingCancel> createdBookingCancels = new ArrayList<>();
        for (BookingCancel bookingCancel : bookingCancels) {
            BookingCancel createdBookingCancel = bookingCancelRepository.save(bookingCancel);
            createdBookingCancels.add(createdBookingCancel);
        }
        return new SuccessAPIResponse(createdBookingCancels);
    }
}

package com.app.service.serviceImpl;

import com.app.dto.BookingDto;
import com.app.dto.PersonBookingDto;
import com.app.entity.Booking;
import com.app.entity.PersonBooking;
import com.app.mapper.BookingMapper;
import com.app.payload.request.BookingQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.BookingRepository;
import com.app.repository.PersonBookingRepository;
import com.app.service.BookingServices;
import com.app.speficication.BookingSpecification;
import com.app.type.EBooking;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceimpl implements BookingServices {
    @Autowired
    BookingMapper bookingMapper;
    @Autowired
    BookingSpecification bookingSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PersonBookingRepository personBookingRepository;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterBooking(BookingQueryParam bookingQueryParam) {
        try {
        Specification<Booking> spec = bookingSpecification.getBookingSpecitification(bookingQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(bookingQueryParam);
        Page<Booking> response = bookingRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }
    @Override
    public APIResponse createBooking(BookingDto bookingDto) {
        try {
            Booking booking = new Booking();
            booking.setNote(bookingDto.getNote());
            booking.setAdult(bookingDto.getAdult());
            booking.setChildren(bookingDto.getChildren());
            booking.setBaby(bookingDto.getBaby());
            booking.setQr(bookingDto.getQr());
            booking.setStatus(bookingDto.getStatus());
            booking.setTourId(bookingDto.getTourId());
            booking.setPaymentId(bookingDto.getPaymentId());
            booking.setAccountId(bookingDto.getAccountId());
            Booking bookingId = bookingRepository.save(booking);
            for (PersonBookingDto personBookingDto : bookingDto.getPersonBookings()) {
                PersonBooking personBooking = new PersonBooking();
                personBooking.setFullName(personBookingDto.getFullName());
                personBooking.setBirthday(personBookingDto.getBirthday());
                personBooking.setGender(personBookingDto.isGender());
                personBooking.setAges(personBookingDto.getAges());
                personBooking.setRelationship(personBookingDto.getRelationship());
                personBooking.setBookingId(bookingId);
                personBookingRepository.save(personBooking);
            }
            return new SuccessAPIResponse(bookingDto);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }
    @Override
    public APIResponse getByStatus(EBooking status, BookingQueryParam bookingQueryParam) {
        try {
            Pageable pageable = requestParamsUtils.getPageable(bookingQueryParam);
            Page<Booking> bookingPage = bookingRepository.getBookingByStatus(status, pageable);
            List<BookingDto> bookingDtos = bookingPage.getContent().stream()
                    .map(bookingMapper::bookingDto)
                    .collect(Collectors.toList());
            Page<BookingDto> response = new PageImpl<>(bookingDtos, pageable, bookingPage.getTotalElements());
            return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }
    @Override
    public APIResponse create(Booking booking) {
        try {
        booking.setCreatedBy("1");
        booking.setCreatedAt(new Date());
        booking = bookingRepository.save(booking);
        return new SuccessAPIResponse(booking);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Booking booking) {
        if(booking == null){
            return  new FailureAPIResponse("Booking id is required!");
        }
        Booking exists = bookingRepository.findById(booking.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find booking with id: "+booking.getId());
        }
        booking = bookingRepository.save(booking);
        return new SuccessAPIResponse(booking);
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            bookingRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Booking.class, bookingRepository);
    }

    @Override
    public APIResponse createBatch(List<Booking> bookings) {
        List<Booking> createdBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            Booking createdBooking = bookingRepository.save(booking);
            createdBookings.add(createdBooking);
        }
        return new SuccessAPIResponse(createdBookings);
    }
}

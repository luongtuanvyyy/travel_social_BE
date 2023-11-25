package com.app.api;

import com.app.entity.BookingCancel;
import com.app.entity.BookingNotification;
import com.app.payload.request.BookingNotificationQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.BookingNotificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingNotificationApi {
    @Autowired
    BookingNotificationServices bookingNotificationServices;

    @GetMapping("/user/booking-notifications")
    public ResponseEntity<?> filterBookingNotification(BookingNotificationQueryParam bookingNotificationQuery) {
        return ResponseEntity.ok(bookingNotificationServices.filterBookingNotification(bookingNotificationQuery));
    }

    @PostMapping("/user/booking-notifications")
    public ResponseEntity<?> createBookingNotification(@RequestPart(name = "booking_notification") BookingNotification bookingNotification) {
        APIResponse response = bookingNotificationServices.create(bookingNotification);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/user/booking-notifications")
    public ResponseEntity<?> updateBookingNotification(@RequestPart(name = "booking_notification") BookingNotification bookingNotification) {
        APIResponse response = bookingNotificationServices.update(bookingNotification);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/user/booking-notifications")
    public ResponseEntity<?> deleteBookingNotification(@RequestParam("id") Integer id) {
        APIResponse response = bookingNotificationServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/booking-notifications/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = bookingNotificationServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/user/booking-notifications/batch")
    public ResponseEntity<?> createBookingNotificationsBatch(@RequestBody List<BookingNotification> bookingNotifications) {
        APIResponse response = bookingNotificationServices.createBatch(bookingNotifications);
        return ResponseEntity.ok().body(response);
    }
}

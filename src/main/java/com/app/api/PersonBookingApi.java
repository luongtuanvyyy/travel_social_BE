package com.app.api;

import com.app.entity.Like;
import com.app.entity.PersonBooking;
import com.app.payload.request.PersonBookingQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.PersonBookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonBookingApi {
    @Autowired
    PersonBookingServices personBookingServices;

    @GetMapping("/user/person-bookings")
    public ResponseEntity<?> filterPersonBooking(PersonBookingQueryParam personBookingQueryParam) {
        return ResponseEntity.ok(personBookingServices.filterPersonBooking(personBookingQueryParam));
    }

    @PostMapping("/user/person-bookings")
    public ResponseEntity<?> createPersonBooking(@RequestPart(name = "person_bookings") PersonBooking personBooking) {
        APIResponse response = personBookingServices.create(personBooking);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/user/person-bookings")
    public ResponseEntity<?> updatePersonBooking(@RequestPart(name = "person_bookings") PersonBooking personBooking) {
        APIResponse response = personBookingServices.update(personBooking);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/user/person-bookings")
    public ResponseEntity<?> deletePersonBooking(@RequestParam("id") Integer id) {
        APIResponse response = personBookingServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/person-bookings/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = personBookingServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/user/person-bookings/batch")
    public ResponseEntity<?> createPersonBookingsBatch(@RequestBody List<PersonBooking> personBookings) {
        APIResponse response = personBookingServices.createBatch(personBookings);
        return ResponseEntity.ok().body(response);
    }
}

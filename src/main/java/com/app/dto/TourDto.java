package com.app.dto;

import com.app.modal.CommentModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {
        private String nameTour;
        private String departure;
        private String image;
        private Integer size;
        private Integer registered;
        private String vehicle;
        private BigDecimal adult;
        private BigDecimal children;
        private BigDecimal baby;
        private Integer discount;
        private String cloudinaryId;
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        private Date startDateBooking;
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        private Date endDateBooking;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private Date startDate;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private Date endDate;
        private String AccName;
        private String avatar;
        private String email;
        private String comment;
        private Float rating;
}

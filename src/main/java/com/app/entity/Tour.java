package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TOUR")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Tour extends BaseEntity {
    private String name;
    private BigDecimal price;
    private String departure;
    private String image;
    private Integer size;
    private Integer registered;
    private Integer percent;
// Can Luu Y
    private String vehicle;

    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;

    @Column(name = "START_DATE_BOOKING")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDateBooking;
    @Column(name = "END_DATE_BOOKING")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endDateBooking;
    @Column(name = "START_DATE")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startDate;
    @Column(name = "END_DATE")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startEnd;

    @ManyToOne
    @JoinColumn(name = "TOUR_TEMPLATE_ID", referencedColumnName = "ID")
    private TourTemplate tourTemplateId;
}

package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOUCHER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Voucher extends BaseEntity {
    @Column(name = "TIME_START")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timeStart;

    @Column(name = "TIME_END")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timeEnd;

    @Column(name = "SIZE")
    private Integer size;

    @Column(name = "PERCENT")
    private Integer percent;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "ID")
    private Tour tour;
}

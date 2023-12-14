package com.app.dto;

import com.app.type.EAges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonBookingDto {
    private String fullName;
    private Date birthday;
    private boolean gender;
    private EAges ages;
    private String relationship;
}

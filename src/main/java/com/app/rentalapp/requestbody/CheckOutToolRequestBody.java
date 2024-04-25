package com.app.rentalapp.requestbody;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// Request body for checking out a tool
public class CheckOutToolRequestBody {
    private final String toolCode;
    private final Integer rentalDays;
    private final Integer discountPercentage; 
    private final LocalDate checkoutDate;
}

package com.app.rentalapp.models;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a rental agreement for a tool rental.
 */
@AllArgsConstructor // Lombok annotation to generate constructor with all fields
@Getter // Lombok annotation to generate getter methods for all fields
@Setter // Lombok annotation to generate setter methods for all fields
public class RentalAgreement {

    private final String toolCode; // Code of the rented tool
    private final String toolType; // Type of the rented tool
    private final String toolBrand; // Brand of the rented tool
    private final int rentalDays; // Number of rental days
    private final int discountPercentage; // Discount percentage applied
    private final LocalDate checkoutDate; // Date when the tool was checked out
    private final LocalDate dueDate; // Due date for returning the tool
    private final double dailyRentalCharge; // Daily rental charge for the tool
    private final int chargeDays; // Number of days the tool is charged for
    private final double preDiscountCharge; // Pre-discount charge for the rental
    private final double discountAmount; // Discount amount
    private final double finalCharge; // Final charge after discount applied

    /**
     * Method to print the rental agreement with details.
     */
    public void printAgreement() {
        // Decimal formatter for currency
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        // Date formatter for formatting dates
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy");

        // Print the rental agreement details
        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + toolBrand);
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Checkout date: " + dateFormat.format(checkoutDate));
        System.out.println("Due date: " + dateFormat.format(dueDate));
        System.out.println("Daily rental charge: " + df.format(dailyRentalCharge));
        System.out.println("Charge days: " + chargeDays);
        System.out.println("Pre-discount charge: " + df.format(preDiscountCharge));
        System.out.println("Discount percent: " + discountPercentage + "%");
        System.out.println("Discount amount: " + df.format(discountAmount));
        System.out.println("Final charge: " + df.format(finalCharge));
        System.out.println("----------------------");
    }
}

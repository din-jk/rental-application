package com.app.rentalapp.models;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a tool available for rental.
 */
@Entity // Marks this class as a JPA entity
@Data // Lombok annotation to generate getter, setter, toString, equals, and hashCode methods
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
@Builder // Lombok annotation to generate a builder pattern
public class Tool {
    
    @Id // Specifies the primary key of the entity
    @GeneratedValue // Specifies that the ID should be generated automatically
    @Column(updatable = false, nullable = false) // Specifies column properties
    private Long id; // Unique identifier for the tool
    
    @Column(unique = true, nullable = false) 
    private String code; // Unique code name for the tool
    
    @Column(nullable = false) 
    private String type; // Type of tool
    
    @Column(nullable = false) 
    private String brand; // Brand name of the tool
    
    @Column(nullable = false) 
    private double dailyCharge; // Daily charge of the tool
    
    @Column(nullable = false) 
    private boolean isWeekdayChargeable; // Indicates if the tool has weekday charges
    
    @Column(nullable = false)
    private boolean isWeekendChargeable; // Indicates if the tool has weekend charges
    
    @Column(nullable = false)
    private boolean isHolidayChargeable; // Indicates if the tool has holiday charges
    
    @CreationTimestamp 
    @Column(updatable = false) 
    private Timestamp dateCreated; // Timestamp of when the tool was first created
    
    @UpdateTimestamp 
    private Timestamp lastModified; // Timestamp of when the tool was last updated
}

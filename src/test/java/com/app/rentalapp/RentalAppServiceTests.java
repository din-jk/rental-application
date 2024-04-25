package com.app.rentalapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.rentalapp.models.RentalAgreement;
import com.app.rentalapp.service.RentalAppService;

@SpringBootTest
// Test class for the ToolRentalService checkout method
public class RentalAppServiceTests {

	@Autowired
    private RentalAppService toolRentalService; // Instance of toolRentalService for testing

    
    // Test case for checkout method - Scenario 1
    @Test
    void test_Scenario1() {
        // Perform checkout operation and get rental agreement
        RentalAgreement agreement = toolRentalService.checkoutTool("JAKR", 5, 101, LocalDate.of(2024, 9, 2));
        assertThat(agreement).isNotNull();
        
    }

    // Test case for checkout method - Scenario 2
    @Test
    void test_Scenario2() {
    	RentalAgreement agreement = toolRentalService.checkoutTool("LADW", 0, 0, LocalDate.of(2024, 9, 1));
    	 assertThat(agreement).isNotNull();
    }

    // Test case for checkout method - Scenario 3
    @Test
    void test_Scenario3() {
    	RentalAgreement agreement = toolRentalService.checkoutTool("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
    	 assertThat(agreement).isNotNull();
    }

    // Test case for checkout method - Scenario 4
    @Test
    void test_Scenario4() {
    	RentalAgreement agreement = toolRentalService.checkoutTool("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
    	 assertThat(agreement).isNotNull();
    }

    // Test case for checkout method - Scenario 5
    @Test
    void test_Scenario5() {
    	RentalAgreement agreement = toolRentalService.checkoutTool("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
    	 assertThat(agreement).isNotNull();
    }

    // Test case for checkout method - Scenario 6
    @Test
    void test_Scenario6() {
    	RentalAgreement agreement = toolRentalService.checkoutTool("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
    	 assertThat(agreement).isNotNull();
    }
}

